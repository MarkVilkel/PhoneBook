package com.nexpay.phonebook.rest.payload;

import com.nexpay.phonebook.tree.Country;

import java.util.Set;
import java.util.stream.Collectors;

public record CountryResponsePayload(String msg, Set<CountryPayload> countries) {
    public static CountryResponsePayload of(String msg, Set<Country> countries) {
        return new CountryResponsePayload(msg, countries.stream().map(CountryPayload::of).collect(Collectors.toSet()));
    }
}
