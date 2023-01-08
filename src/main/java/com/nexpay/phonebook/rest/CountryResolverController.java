package com.nexpay.phonebook.rest;

import com.nexpay.phonebook.resolver.CountryResolver;
import com.nexpay.phonebook.rest.payload.CountryRequestPayload;
import com.nexpay.phonebook.rest.payload.CountryResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class CountryResolverController {
    @Autowired
    private CountryResolver countryResolver;

    @PostMapping("/country/resolve")
    public ResponseEntity<?> getSearchResultViaAjax(
            @Valid @RequestBody CountryRequestPayload search,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            var error = errors
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(","));
            return ResponseEntity.badRequest().body(new CountryResponsePayload(error, null, null));
        }

        var codeCountries = countryResolver.resolve(search.phone());
        return ResponseEntity.ok(CountryResponsePayload.of(codeCountries));
    }

}