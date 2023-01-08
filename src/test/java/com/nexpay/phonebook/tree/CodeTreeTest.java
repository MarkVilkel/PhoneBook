package com.nexpay.phonebook.tree;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeTreeTest {

    @Test
    void putGet() {
        var tree = new CodeTree();

        tree.put("1", new Country("USA"));
        assertEquals(new CodeCountries("1", Set.of(new Country("USA"))), tree.getCountries("1"));

        tree.put("1", new Country("Canada"));
        assertEquals(new CodeCountries("1", Set.of(new Country("USA"), new Country("Canada"))), tree.getCountries("1"));

        tree.put("12", new Country("US Texas"));
        assertEquals(new CodeCountries("12", Set.of(new Country("US Texas"))), tree.getCountries("12"));

        tree.put("12", new Country("Canada Ontario"));
        assertEquals(new CodeCountries("12", Set.of(new Country("US Texas"), new Country("Canada Ontario"))), tree.getCountries("12"));
        assertEquals(new CodeCountries("1", Set.of(new Country("USA"), new Country("Canada"))), tree.getCountries("1"));
    }

}
