package com.nexpay.phonebook.resolver;

import com.nexpay.phonebook.tree.Country;

import java.util.Set;

public interface CountryResolver {

    Set<Country> resolve(String phoneNumber);

}
