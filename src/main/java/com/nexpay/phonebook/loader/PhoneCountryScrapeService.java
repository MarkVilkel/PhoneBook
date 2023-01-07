package com.nexpay.phonebook.loader;

import com.nexpay.phonebook.tree.Country;
import com.nexpay.phonebook.tree.PhoneTree;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class PhoneCountryScrapeService implements PhoneCountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneCountryScrapeService.class);


    @Value("${phone.country.data.source.url}")
    private String url;

    private PhoneTree tree;

    @PostConstruct
    public void init() throws IOException {
        tree = scrapePhoneTree();
    }

    @Override
    public PhoneTree scrapePhoneTree() throws IOException {
        LOGGER.info("Scraping phone codes and countries from {}", url);

        var tree = new PhoneTree();

        var document = Jsoup.connect(url).get();
        var tables = document.select("table");

        //The first table is that what we want for this task
        var table = tables.first();
        if (table == null) {
            throw new IllegalArgumentException("html format has changed");
        }

        var rows = table.select("tr");
        for (int rowIndex = 1; rowIndex < rows.size(); rowIndex++) { //zero row contains headers
            Element row = rows.get(rowIndex);
            Elements columns = row.select("td");

            for (int columnIndex = 0; columnIndex < columns.size(); columnIndex++) {
                var column = columns.get(columnIndex);
                var aTags = column.select("a");
                Integer currentPhoneCode = null;
                Set<Country> currentCountries = new LinkedHashSet<>();

                for (Element aTag : aTags) {
                    var phoneCode = tryToGetPhoneCode(aTag);
                    if (phoneCode != null) {
                        if (currentPhoneCode != null) {
                            tree.put(currentPhoneCode, currentCountries);
                        }
                        currentPhoneCode = phoneCode;
                        currentCountries = new LinkedHashSet<>();
                    } else {
                        if (currentPhoneCode != null && currentPhoneCode == 1 && currentCountries.size() == 2) {
                            //special case for US and Canada, the rest code will come in the next row
                            break;
                        }
                        var countryCode = aTag.text();
                        if ("North American Numbering Plan".equalsIgnoreCase(countryCode)) {
                            // special case - this is not a country
                            continue;
                        }
                        var countryName = aTag.attr("title");
                        currentCountries.add(new Country(countryCode, countryName));
                    }
                }
                if (currentPhoneCode != null && !currentCountries.isEmpty()) {
                    tree.put(currentPhoneCode, currentCountries);
                }
            }
        }
        LOGGER.info("Phone codes and countries loaded {}", tree);
        return tree;
    }

    private Integer tryToGetPhoneCode(Element element) {
        try {
            var phoneCode = element.text();
            phoneCode = phoneCode.replaceAll(" ", "").replaceAll("\\+", "");
            return Integer.parseInt(phoneCode);
        } catch (Exception e) {
            return null;
        }
    }

}
