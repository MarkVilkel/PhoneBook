package com.nexpay.phonebook.rest.payload;

import com.nexpay.phonebook.tree.CodeCountries;

import java.util.Set;
import java.util.stream.Collectors;

public record CountryResponsePayload(boolean error, String msg, String phoneCode, Set<CountryPayload> countries) {
    public static CountryResponsePayload of(CodeCountries codeCountries) {
        final String msg;
        final boolean error;
        if (codeCountries.countries().isEmpty()) {
            msg = "Country is not resolved!";
            error = true;
        } else {
            msg = "Success";
            error = false;
        }
        return of(error, msg, codeCountries);
    }

    public static CountryResponsePayload of(String msg, CodeCountries codeCountries) {
        return of(false, msg, codeCountries);
    }

    public static CountryResponsePayload error(String msg) {
        return of(true, msg, CodeCountries.of());
    }

    private static CountryResponsePayload of(boolean error, String msg, CodeCountries codeCountries) {
        return new CountryResponsePayload(
                error,
                msg,
                codeCountries.phoneCode(),
                codeCountries.countries().stream().map(CountryPayload::of).collect(Collectors.toSet())
        );
    }
}
