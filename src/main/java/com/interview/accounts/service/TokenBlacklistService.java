package com.interview.accounts.service;

public interface TokenBlacklistService {
    void addToBlacklist(String token);
    boolean isTokenBlacklisted(String token);
}
