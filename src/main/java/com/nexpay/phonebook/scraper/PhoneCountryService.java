package com.nexpay.phonebook.scraper;

import java.io.IOException;
import java.util.List;

public interface PhoneCountryService {

    List<PhoneCodeCountry> scrapeCodes() throws IOException;
}
