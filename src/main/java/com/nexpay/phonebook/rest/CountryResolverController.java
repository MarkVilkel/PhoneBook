package com.nexpay.phonebook.rest;

import com.nexpay.phonebook.resolver.CountryResolver;
import com.nexpay.phonebook.rest.payload.CountryRequestPayload;
import com.nexpay.phonebook.rest.payload.CountryResponsePayload;
import com.nexpay.phonebook.rest.validation.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CountryResolverController {
    @Autowired
    private CountryResolver countryResolver;

    @PostMapping("/country/resolve")
    public ResponseEntity<CountryResponsePayload> resolveCountry(
            @Valid @RequestBody CountryRequestPayload request
    ) {
        var codeCountries = countryResolver.resolve(request.phone());
        if (codeCountries == null) {
            throw CountryNotFoundException.of(request.phone());
        }
        return ResponseEntity.ok(CountryResponsePayload.of(codeCountries));
    }

}