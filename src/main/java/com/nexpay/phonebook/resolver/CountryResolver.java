package com.nexpay.phonebook.resolver;

import com.nexpay.phonebook.tree.CodeCountries;

public interface CountryResolver {

    /**
     * The method resolves countries by phone number.
     * Example: phoneNumber starts with digit 123454535435, then at least two countries are returned: US and Canada and also country code 1
     * @param phoneNumber as string
     * @return object containing resolved country code and countries list
     */
    CodeCountries resolve(String phoneNumber);

}
