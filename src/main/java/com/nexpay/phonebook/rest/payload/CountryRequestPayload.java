package com.nexpay.phonebook.rest.payload;

import javax.validation.constraints.NotBlank;

public record CountryRequestPayload(
        @NotBlank(message = "The phone is absent!") String phone
) {
}
