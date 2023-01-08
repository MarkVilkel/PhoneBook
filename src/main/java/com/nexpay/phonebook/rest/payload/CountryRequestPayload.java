package com.nexpay.phonebook.rest.payload;

import com.nexpay.phonebook.rest.validation.PhoneNumber;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record CountryRequestPayload(
        @NotBlank(message = "The phone is absent")
        @PhoneNumber(message = "The phone format is invalid")
        @Length(min = 5, max = 15, message = "The phone number length must be in interval [5; 15]")
        String phone
) {
}
