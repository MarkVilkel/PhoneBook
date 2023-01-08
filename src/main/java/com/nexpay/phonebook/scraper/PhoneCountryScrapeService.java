package com.nexpay.phonebook.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneCountryScrapeService implements PhoneCountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneCountryScrapeService.class);

    @Value("${phone.country.data.source.url}")
    private String url;

    @Override
    public List<PhoneCodeCountry> scrapeCodes() throws IOException {
        LOGGER.info("Scraping phone codes and countries from {}", url);

        List<PhoneCodeCountry> result = new ArrayList<>();

        var document = Jsoup.connect(url).get();
        var tables = document.getElementsByClass("wikitable sortable");

        //The first table is that what we want for this task
        var table = tables.first();
        if (table == null) {
            throw new IllegalArgumentException("html format has changed");
        }

        var rows = table.select("tr");
        for (int rowIndex = 1; rowIndex < rows.size(); rowIndex++) { //zero row contains headers
            Element row = rows.get(rowIndex);
            Elements columns = row.select("td");
            var country = columns.get(0).text();
            var phoneCodeColumn = columns.get(1);
            var phoneCodes = split(phoneCodeColumn.text());
            phoneCodes.forEach(i -> result.add(new PhoneCodeCountry(i, country)));
        }

        LOGGER.info("Phone codes and countries are loaded {}", result);
        return result;
    }

    private Set<String> split(String phonesStr) {
        var phoneCodes = phonesStr.split(",");
        return Arrays.stream(phoneCodes).map(this::tryToGetPhoneCode).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    private String tryToGetPhoneCode(String phoneCode) {
        try {
            var cleanPhoneCode = phoneCode
                    .replaceAll("until 2025", "")
                    .replaceAll("notes 1", "")
                    .replaceAll("\\D", "")
            ;
            Integer.parseInt(cleanPhoneCode);// just in case if it is not only numbers then exception will be thrown
            return cleanPhoneCode;
        } catch (Exception e) {
            return null;
        }
    }

}
