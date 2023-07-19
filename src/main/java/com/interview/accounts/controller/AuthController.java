package com.interview.accounts.controller;

import com.interview.accounts.domain.JwtResponse;
import com.interview.accounts.model.JwtRequest;
import com.interview.accounts.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseEntity<JwtResponse> login(@RequestBody  @Valid JwtRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseEntity<String> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization){
        return new ResponseEntity<>(authService.logout(authorization), HttpStatus.OK);
    }



}