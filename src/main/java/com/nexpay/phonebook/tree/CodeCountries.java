package com.nexpay.phonebook.tree;

import java.util.Set;

public record CodeCountries(String phoneCode, Set<Country> countries) {
    public static CodeCountries of() {
        return new CodeCountries(null, Set.of());
    }
}
