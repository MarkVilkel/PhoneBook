package com.nexpay.phonebook.rest.validation;

public class CountryNotFoundException extends RuntimeException {

    private CountryNotFoundException(String message) {
        super(message);
    }

    public static CountryNotFoundException of(String phoneNumber) {
        return new CountryNotFoundException("Country is not resolved by phone " + phoneNumber);
    }

}