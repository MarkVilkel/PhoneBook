package com.nexpay.phonebook.resolver;

import com.nexpay.phonebook.tree.CodeCountries;

public interface CountryResolver {

    CodeCountries resolve(String phoneNumber);

}
