package com.animo.animorise.controller;

import com.animo.animorise.dto.LoginUserDto;
import com.animo.animorise.dto.RegisterUserDto;
import com.animo.animorise.entity.User;
import com.animo.animorise.response.LoginResponse;
import com.animo.animorise.service.AuthenticationService;
import com.animo.animorise.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // 🔹 Generate JWT using the interface, ensuring flexibility
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime())
                .setUser(Map.of("role", authenticatedUser.getRole().name()));

        return ResponseEntity.ok(loginResponse);
    }
}
