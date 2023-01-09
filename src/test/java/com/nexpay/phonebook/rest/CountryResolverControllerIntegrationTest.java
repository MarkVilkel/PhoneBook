package com.nexpay.phonebook.rest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class CountryResolverControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void successfullyResolveLatvia() throws Exception {
        var request = new JSONObject();
        request.put("phone", "37128236723");
        var response = postCountryResolve(request);
        assertNotNull(response);
        assertFalse(response.getBoolean("error"));
        assertEquals("Success", response.get("msg"));
        assertEquals("371", response.get("phoneCode"));
        assertEquals("Latvia", response.getJSONArray("countries").getJSONObject(0).get("name"));
    }

    @Test
    void successfullyResolveLithuania() throws Exception {
        var request = new JSONObject();
        request.put("phone", "3701231312");
        var response = postCountryResolve(request);
        assertNotNull(response);
        assertFalse(response.getBoolean("error"));
        assertEquals("Success", response.get("msg"));
        assertEquals("370", response.get("phoneCode"));
        assertEquals("Lithuania", response.getJSONArray("countries").getJSONObject(0).get("name"));
    }

    @Test
    void successfullyResolveCanadaAndUS() throws Exception {
        var request = new JSONObject();
        request.put("phone", "13701231312");
        var response = postCountryResolve(request);
        assertNotNull(response);
        assertFalse(response.getBoolean("error"));
        assertEquals("Success", response.get("msg"));
        assertEquals("1", response.get("phoneCode"));
        assertEquals("Canada", response.getJSONArray("countries").getJSONObject(0).get("name"));
        assertEquals("United States", response.getJSONArray("countries").getJSONObject(1).get("name"));
    }

    @Test
    void failToResolveCountry() throws Exception {
        var request = new JSONObject();
        var phone = "00013701231312";
        request.put("phone", phone);
        var response = postCountryResolve(request);
        assertNotNull(response);
        assertTrue(response.getBoolean("error"));
        assertEquals("Country is not resolved by phone " + phone, response.get("msg"));
        assertEquals("null", response.optString("phoneCode"));
        assertEquals(new JSONArray(), response.optJSONArray("countries"));
    }

    JSONObject postCountryResolve(
            JSONObject request
    ) throws Exception {
        return postRequest("/country/resolve", request);
    }

    JSONObject postRequest(
            String path,
            JSONObject request
    ) throws Exception {
        var builder = post(path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(request.toString());

        var responseContent = mockMvc.perform(builder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

        return new JSONObject(responseContent);
    }

}
