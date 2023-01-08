package com.nexpay.phonebook.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CodeTree {
    private final Map<String, TreeNode> tree = new HashMap<>();

    public void put(String phoneCode, Country country) {
        var node = tree.get(phoneCode);
        if (node != null) {
            node.add(country);
        } else {
            if (phoneCode.length() == 1) {
                tree.put(phoneCode, TreeNode.of(phoneCode, country));
            } else {
                var currentTree = tree;
                for (int i = 1; i < phoneCode.length(); i ++) {
                    var subCode = phoneCode.substring(0, i);
                    var subCodeNode = currentTree.get(subCode);
                    if (subCodeNode != null) {
                        currentTree = subCodeNode.children();
                    }
                }
                node = currentTree.get(phoneCode);
                if (node == null) {
                    currentTree.put(phoneCode, TreeNode.of(phoneCode, country));
                } else {
                    node.add(country);
                }
            }
        }
    }

    public CodeCountries getCountries(String phone) {
        var node = getCountriesNode(phone);
        return node == null ? null : new CodeCountries(node.code(), Set.copyOf(node.countries()));
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
            for (int i = 1; i <= phone.length(); i ++) {
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
        CodeTree codeTree = (CodeTree) o;
        return Objects.equals(tree, codeTree.tree);
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
