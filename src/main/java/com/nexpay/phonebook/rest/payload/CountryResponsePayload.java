package com.nexpay.phonebook.rest.payload;

import com.nexpay.phonebook.tree.CodeCountries;

import java.util.Set;
import java.util.stream.Collectors;

public record CountryResponsePayload(String msg, String phoneCode, Set<CountryPayload> countries) {
    public static CountryResponsePayload of(CodeCountries codeCountries) {
        final String msg;
        if (codeCountries.countries().isEmpty()) {
            msg = "Country is not resolved!";
        } else {
            msg = "Success";
        }
        return of(msg, codeCountries);
    }
    public static CountryResponsePayload of(String msg, CodeCountries codeCountries) {
        return new CountryResponsePayload(
                msg,
                codeCountries.phoneCode(),
                codeCountries.countries().stream().map(CountryPayload::of).collect(Collectors.toSet())
        );
    }
}
