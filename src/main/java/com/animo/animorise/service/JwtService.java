package com.animo.animorise.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    Claims extractAllClaims(String token);
    long getExpirationTime();
    void setJwtExpiration(long jwtExpiration);
}
