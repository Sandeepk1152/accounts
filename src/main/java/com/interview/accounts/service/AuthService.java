package com.interview.accounts.service;

import com.interview.accounts.config.JwtHelper;
import com.interview.accounts.domain.JwtResponse;
import com.interview.accounts.model.JwtRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;
    // database resource can be used to check if previous token is generated for current user
    private Map<JwtRequest, String> jwtMap = new HashMap<>();

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);


    public JwtResponse login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getUserName(), request.getPassword());
        if (jwtMap.get(request) != null) {
            throw new BadCredentialsException("User has already generated token please use previously generated token");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.helper.generateToken(userDetails);

        // pushing token to prevent regeenration of token
        jwtMap.put(request, token);

        return JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }


    public String logout(String authorization) {
        tokenBlacklistService.addToBlacklist(authorization.replace("Bearer ", ""));
        return "Logged out successfully";
    }
}
