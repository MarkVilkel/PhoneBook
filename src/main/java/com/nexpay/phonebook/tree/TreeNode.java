package com.nexpay.phonebook.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public record TreeNode(String code, Set<Country> countries, Map<String, TreeNode> children) {
    public static TreeNode of(String code, Country country) {
        Set<Country> countries = new HashSet<>();
        countries.add(country);
        return new TreeNode(code, countries, new HashMap<>());
    }

    public void add(Country country) {
        countries.add(country);
    }
}
