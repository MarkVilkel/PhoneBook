package com.nexpay.phonebook.resolver;

import com.nexpay.phonebook.scraper.PhoneCountryService;
import com.nexpay.phonebook.tree.CodeTree;
import com.nexpay.phonebook.tree.Country;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Comparator;
import java.util.Set;

@Service
public class CountryCachedResolver implements CountryResolver {

    private final PhoneCountryService phoneCountryService;

    private CodeTree tree;


    public CountryCachedResolver(PhoneCountryService phoneCountryService) {
        this.phoneCountryService = phoneCountryService;
    }

    @PostConstruct
    public void init() throws IOException {
        var codes = phoneCountryService.scrapeCodes();
        codes.sort(Comparator.comparingInt(o -> o.phoneCode().length()));
        tree = new CodeTree();
        for (var c : codes) {
            tree.put(c.phoneCode(), new Country(c.country()));
        }
    }

    @Override
    public Set<Country> resolve(String phoneNumber) {
        return tree.getCountries(phoneNumber);
    }
}
