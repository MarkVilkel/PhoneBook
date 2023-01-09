package com.nexpay.phonebook.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexpay.phonebook.rest.payload.CountryRequestPayload;
import com.nexpay.phonebook.rest.payload.CountryResponsePayload;
import com.nexpay.phonebook.tree.CodeCountries;
import com.nexpay.phonebook.tree.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryResolverController.class)
class CountryResolverControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryResolverController service;

    @Autowired
    ObjectMapper mapper;

    @Test
    void successfullyResolveCountry() throws Exception {
        var request = new CountryRequestPayload("37128236723");
        var response = CountryResponsePayload.of(new CodeCountries("371", Set.of(new Country("Latvia"))));

        when(service.resolveCountry(any())).thenReturn(ResponseEntity.ok(response));

        mockMvc
                .perform(post("/country/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"msg\":\"Success\",\"phoneCode\":\"371\",\"countries\":[{\"name\":\"Latvia\"}]}\"")
                );
    }

    @Test
    void resolveCountryWithError() throws Exception {
        var phone = "37128236723";
        var request = new CountryRequestPayload(phone);
        var response = CountryResponsePayload.error("Country is not resolved by phone number " + phone);

        when(service.resolveCountry(any())).thenReturn(ResponseEntity.ok(response));

        mockMvc
                .perform(post("/country/resolve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"msg\":\"Country is not resolved by phone number " + phone + "\",\"phoneCode\":null,\"countries\":[]}\"")
                );
    }

}
