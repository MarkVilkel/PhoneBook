package com.nexpay.phonebook.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PhoneTree {
    private final Map<String, TreeNode> tree = new HashMap<>();

    public void put(int phoneCode, Set<Country> countries) {
        put(String.valueOf(phoneCode), countries);
    }
    public void put(String phoneCode, Set<Country> countries) {
        put(phoneCode, TreeNode.of(countries));
    }

    public void put(String phoneCode, TreeNode node) {
        if (phoneCode.length() == 1) {
            tree.put(phoneCode, node);
        } else {
            var currentTree = tree;
            for (int i = 1; i < phoneCode.length(); i ++) {
                var subCode = phoneCode.substring(0, i);
                var subCodeNode = currentTree.get(subCode);
                if (subCodeNode != null) {
                    currentTree = subCodeNode.children();
                }
            }
            currentTree.put(phoneCode, node);
        }
    }

    public Set<Country> getCountries(String phone) {
        var node = getCountriesNode(phone);
        return node == null ? Set.of() : Set.copyOf(node.countries());
    }

    private TreeNode getCountriesNode(String phone) {
        if (phone == null || phone.isEmpty()) {
            return null;
        }

        if (phone.length() == 1) {
            return tree.get(phone);
        } else {
            var currentTree = tree;
            TreeNode lastNode = null;
            for (int i = 1; i < phone.length(); i ++) {
                var subCode = phone.substring(0, i);
                var node = currentTree.get(subCode);
                if (node != null) {
                    lastNode = node;
                    currentTree = node.children();
                }
            }
            return lastNode;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneTree phoneTree = (PhoneTree) o;
        return Objects.equals(tree, phoneTree.tree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tree);
    }

    @Override
    public String toString() {
        return "PhoneTree{" +
                "tree=" + tree +
                '}';
    }
}
