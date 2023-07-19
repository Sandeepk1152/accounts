package com.interview.accounts.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.accounts.domain.JwtResponse;
import com.interview.accounts.model.GetAccountsResponseBody;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest {

    @LocalServerPort
    private int port;

    private static RestTemplate restTemplate;

    private String BASE_URL = "http://localhost";

    private static String  JWT_TOKEN_TEST=null;


    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
        Unirest.setTimeouts(0, 0);
    }


    @BeforeEach
    public void setUp() {
        BASE_URL = BASE_URL.concat(":").concat(port + "");

    }

    @Test
    @Order(1)
    void login() throws UnirestException, JsonProcessingException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(BASE_URL+"/auth/login")
                .header("Content-Type", "application/json")
                .body("{\r\n \"userName\":\"SANDEEP\",\r\n \"password\": \"SANDEEP\"\r\n}")
                .asString();

        JwtResponse jwtResponse= new ObjectMapper().readValue(response.getBody(), JwtResponse.class);
        JWT_TOKEN_TEST=jwtResponse.getJwtToken();
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertTrue(response.getBody()!=null);
    }

    @Test
    @Order(2)
    void loginWithNoRequestBody() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(BASE_URL+"/auth/login")
                .header("Content-Type", "application/json")
                .asString();

        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertTrue(response.getBody()!=null);
    }

    @Test
    @Order(4)
    @Profile("test")
    void logout() throws UnirestException, JsonProcessingException {


        Unirest.setTimeouts(0, 0);
        HttpResponse<String> logoutResponse = Unirest.post(BASE_URL+"/auth/logout")
                .header("Authorization", "Bearer "+JWT_TOKEN_TEST)
                .header("Content-Type", "application/json")
                .asString();

        Assertions.assertEquals(200,logoutResponse.getStatus());
        Assertions.assertEquals("Logged out successfully",logoutResponse.getBody());
    }

    @Test
    void logoutWithNoToken() throws UnirestException,JsonProcessingException{

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> logoutResponse = Unirest.post(BASE_URL+"/auth/logout")
                .header("Content-Type", "application/json")
                .asString();

        Assertions.assertEquals(401,logoutResponse.getStatus());
    }
}
