package com.nexpay.phonebook.scraper;

import java.io.IOException;
import java.util.List;

public interface PhoneCountryService {

    /**
     * The method scrapes from web and returns countries list according to their phone codes
     * Might fail when 3-rd party html is changed
     *
     * @return the list of countries and their phone codes
     * @throws IOException when no connection to Internet or some other errors appears
     */
    List<PhoneCodeCountry> scrapeCodes() throws IOException;
}
