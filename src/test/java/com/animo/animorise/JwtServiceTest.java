package com.animo.animorise;

import com.animo.animorise.service.JwtService;
import com.animo.animorise.service.impl.JwtServiceImpl;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    @InjectMocks
    private JwtServiceImpl jwtService;


    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Set up a mock UserDetails object
        userDetails = User.withUsername("john.doe@example.com")
                .password("password123")
                .authorities(Collections.emptyList())
                .build();

        // Set the secret key and expiration time for testing
        jwtService.setSecretKey("32df322324cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b");
        jwtService.setJwtExpiration(3600000L); // 1 hour expiration
    }

    // Test for token generation
    @Test
    void generateToken_ValidUserDetails_ReturnsToken() {
        // Act
        String token = jwtService.generateToken(userDetails);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // Test for token generation with extra claims
    @Test
    void generateToken_WithExtraClaims_ReturnsToken() {
        // Arrange
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");

        // Act
        String token = jwtService.generateToken(extraClaims, userDetails);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    // Test for extracting username from token
    @Test
    void extractUsername_ValidToken_ReturnsUsername() {
        // Arrange
        String token = jwtService.generateToken(userDetails);

        // Act
        String username = jwtService.extractUsername(token);

        // Assert
        assertEquals(userDetails.getUsername(), username);
    }

    // Test for token validation with valid user
    @Test
    void isTokenValid_ValidTokenAndUser_ReturnsTrue() {
        // Arrange
        String token = jwtService.generateToken(userDetails);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    // Test for token validation with invalid user
    @Test
    void isTokenValid_InvalidUser_ReturnsFalse() {
        // Arrange
        String token = jwtService.generateToken(userDetails);

        UserDetails invalidUserDetails = User.withUsername("jane.doe@example.com")
                .password("password123")
                .authorities(Collections.emptyList())
                .build();

        // Act
        boolean isValid = jwtService.isTokenValid(token, invalidUserDetails);

        // Assert
        assertFalse(isValid);
    }

    // Test for token expiration with an expired token
    @Test
    void isTokenExpired_ExpiredToken_ReturnsTrue() throws InterruptedException {
        // Arrange
        jwtService.setJwtExpiration(500L); // Set expiration time to 500 milliseconds
        String token = jwtService.generateToken(userDetails);

        Thread.sleep(600); // Wait 600 milliseconds (ensuring token expiry)

        // Act
        boolean isExpired = jwtService.isTokenExpired(token);

        // Assert
        assertTrue(isExpired);
    }


    // Test for token expiration with a valid (non-expired) token
    @Test
    void isTokenExpired_ValidToken_ReturnsFalse() {
        // Arrange
        jwtService.setJwtExpiration(3600000L); // Set expiration time to 1 hour
        String token = jwtService.generateToken(userDetails);

        // Act
        boolean isExpired = jwtService.isTokenExpired(token);

        // Assert
        assertFalse(isExpired);
    }

    // Test for extracting a custom claim from the token
    @Test
    void extractClaim_CustomClaim_ReturnsClaimValue() {
        // Arrange
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");

        String token = jwtService.generateToken(extraClaims, userDetails);

        // Act
        String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));

        // Assert
        assertEquals("ADMIN", role);
    }

    // Test for extracting all claims from the token
    @Test
    void extractAllClaims_ValidToken_ReturnsClaims() {
        // Arrange
        String token = jwtService.generateToken(userDetails);

        // Act
        Claims claims = jwtService.extractAllClaims(token);

        // Assert
        assertNotNull(claims);
        assertEquals(userDetails.getUsername(), claims.getSubject());
    }


}