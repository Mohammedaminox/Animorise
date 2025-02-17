package com.animo.animorise.service.impl;

import com.animo.animorise.dto.LoginUserDto;
import com.animo.animorise.dto.RegisterUserDto;
import com.animo.animorise.entity.Role;
import com.animo.animorise.entity.User;
import com.animo.animorise.exception.AuthenticationFailedException;
import com.animo.animorise.exception.UserAlreadyExistsException;
import com.animo.animorise.repository.UserRepository;
import com.animo.animorise.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signup(RegisterUserDto registerUserDto) {
        log.info("Registering user with email: {}", registerUserDto.getEmail());

        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            log.warn("User with email {} already exists", registerUserDto.getEmail());
            throw new UserAlreadyExistsException("User with this email already exists");
        }
        User user = User.builder()
                .fullName(registerUserDto.getFullName())
                .email(registerUserDto.getEmail())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .role(Role.USER)
                .build();

        log.info("User registered successfully: {}", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User authenticate(LoginUserDto loginUserDto) {
        log.info("Authenticating user with email: {}", loginUserDto.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUserDto.getEmail(),
                            loginUserDto.getPassword()
                    )
            );
        } catch (AuthenticationException ex) {
            log.error("Authentication failed for user: {}", loginUserDto.getEmail());
            throw new AuthenticationFailedException("Invalid email or password");
        }

        log.info("User authenticated successfully: {}", loginUserDto.getEmail());
        return userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new AuthenticationFailedException("User not found"));
    }
}
