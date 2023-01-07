package com.nexpay.phonebook.tree;

public record Country(String code, String name) {
    public static Country of(String code, String name) {
        return new Country(code, name);
    }
}
