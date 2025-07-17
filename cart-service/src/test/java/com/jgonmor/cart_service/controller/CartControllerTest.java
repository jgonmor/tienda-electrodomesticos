package com.jgonmor.cart_service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CartControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void whenGetCart_Status200() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(
                "/cart/1",
                HttpMethod.GET,
                null,
                String.class
        );

        assert response.getStatusCode().is2xxSuccessful();
    }
}
