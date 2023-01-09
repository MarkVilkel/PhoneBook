package com.nexpay.phonebook.rest.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    @Override
    public void initialize(PhoneNumber phoneNumber) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cxt) {
        if (value == null) {
            return true;
        }
        // Decided to keep things simple for this task so the phone number is just digits, without any '+', '-' and '()' symbols
        return Pattern.matches("[0-9]+", value);
    }

}