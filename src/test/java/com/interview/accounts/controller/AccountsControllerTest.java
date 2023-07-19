package com.interview.accounts.controller;

import com.interview.accounts.model.GetAccountsResponseBody;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AccountsControllerTest {

    @LocalServerPort
    private int port;

    private String BASE_URL = "http://localhost";

    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
        Unirest.setTimeouts(0, 0);
    }

    @BeforeEach
    public void setUp() {
        BASE_URL = BASE_URL.concat(":").concat(port + "").concat("/accounts");

    }

    @Test
    void testAllAccounts() {
        final ResponseEntity<GetAccountsResponseBody> response = restTemplate.getForEntity(BASE_URL, GetAccountsResponseBody.class);

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(1, response.getBody().getTotal());
    }

    @Test
    void findPaginated() {

        String url = BASE_URL + "/findPaginated?page={pagee}&size={sizee}";
        Map<String, Integer> map = new HashMap<>();
        map.put("pagee", 1);
        map.put("sizee", 5);
        final ResponseEntity<GetAccountsResponseBody> response = restTemplate.getForEntity(url, GetAccountsResponseBody.class, map);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    void findPaginatedWithNoparameter() {
        String url = BASE_URL + "/findPaginated";
        try {
            final ResponseEntity<GetAccountsResponseBody> response = restTemplate.getForEntity(url, GetAccountsResponseBody.class);
        } catch (HttpClientErrorException.BadRequest badRequest) {
            assertThat(badRequest)
                    .isInstanceOf(HttpClientErrorException.BadRequest.class);
        }
    }

    @Test
    void filter() {
        String url = BASE_URL + "/filter?queryParameter={parameter}";
        Map<String, String> map = new HashMap<>();
        map.put("parameter", "Accounts29");
        final ResponseEntity<GetAccountsResponseBody> response = restTemplate.getForEntity(url, GetAccountsResponseBody.class, map);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test()
    void filterWithNullParameter() {
        String url = BASE_URL + "/filter?queryParameter={parameter}";
        Map<String, String> map = new HashMap<>();
        map.put("parameter", null);
        try {
            final ResponseEntity<GetAccountsResponseBody> response = restTemplate.getForEntity(url, GetAccountsResponseBody.class, map);
        } catch (HttpClientErrorException.BadRequest badRequest) {
            assertThat(badRequest)
                    .isInstanceOf(HttpClientErrorException.BadRequest.class);
        }
    }

    @Test
    void filter_with_no_result() {
        String url = BASE_URL + "/filter?queryParameter={parameter}";
        Map<String, String> map = new HashMap<>();
        map.put("parameter", UUID.randomUUID().toString());
        final ResponseEntity<GetAccountsResponseBody> response = restTemplate.getForEntity(url, GetAccountsResponseBody.class, map);

        Assertions.assertEquals(200, response.getStatusCode().value());

    }


    @Test
    void updateWithNoRequestBody() throws UnirestException {

        HttpResponse<String> response = Unirest.put("http://localhost:" + port + "/accounts")
                .header("Content-Type", "application/json").asString();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void save() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:" + port + "/accounts")
                .header("Content-Type", "application/json")
                .body("{\r\n    \r\n    \"name\": \"zz\",\r\n    \"number\": 12334,\r\n    \"balance\": 323.3\r\n}")
                .asString();

        Assertions.assertEquals(200, response.getStatus());


    }

    @Test
    void saveWithNoRequestBody() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:" + port + "/accounts")
                .header("Content-Type", "application/json")
                .asString();

        Assertions.assertEquals(400, response.getStatus());
    }
}