package com.nexpay.phonebook.rest.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneNumberValidatorTest {

    private final PhoneNumberValidator validator = new PhoneNumberValidator();

    @Test
    void validPhoneNumber() {
        assertTrue(validator.isValid("37112312312", null));
        assertTrue(validator.isValid("123312", null));
        assertTrue(validator.isValid("1", null));
        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid("1234567890", null));
    }

    @Test
    void invalidPhoneNumber() {
        assertFalse(validator.isValid("+37112312312", null));
        assertFalse(validator.isValid("1a23312", null));
        assertFalse(validator.isValid("", null));
        assertFalse(validator.isValid("-1234567890", null));
    }

}
