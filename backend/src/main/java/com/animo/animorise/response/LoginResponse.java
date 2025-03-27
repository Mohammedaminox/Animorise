package com.animo.animorise.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private long expiresIn;
    private Map<String, String> user;

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public LoginResponse setUser(Map<String, String> user) {
        this.user = user;
        return this;
    }
}
