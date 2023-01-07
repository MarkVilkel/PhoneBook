package com.nexpay.phonebook.rest.payload;

import com.nexpay.phonebook.tree.Country;

public record CountryPayload(String name) {
    public static CountryPayload of(Country country) {
        return new CountryPayload(country.name());
    }
}
