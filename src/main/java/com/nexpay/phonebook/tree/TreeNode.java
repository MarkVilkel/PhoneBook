package com.nexpay.phonebook.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public record TreeNode(Set<Country> countries, Map<String, TreeNode> children) {
    public static TreeNode of(Set<Country> countries) {
        return new TreeNode(countries, new HashMap<>());
    }
}
