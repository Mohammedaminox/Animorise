package com.animo.animorise;

import com.animo.animorise.dto.LoginUserDto;
import com.animo.animorise.dto.RegisterUserDto;
import com.animo.animorise.entity.User;
import com.animo.animorise.exception.AuthenticationFailedException;
import com.animo.animorise.exception.UserAlreadyExistsException;
import com.animo.animorise.repository.UserRepository;
import com.animo.animorise.service.AuthenticationService;
import com.animo.animorise.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationServiceimpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    // Test for successful user registration
    @Test
    void signup_ValidInput_ReturnsUser() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setFullName("John Doe");
        registerUserDto.setEmail("john.doe@example.com");
        registerUserDto.setPassword("password123");

        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(new User());

        // Act
        User user = authenticationServiceimpl.signup(registerUserDto);

        // Assert
        assertNotNull(user);
        verify(userRepository, times(1)).save(any());
    }

    // Test for registration with an existing email
    @Test
    void signup_ExistingEmail_ThrowsUserAlreadyExistsException() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("john.doe@example.com");

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> authenticationServiceimpl.signup(registerUserDto));
    }

    // Test for successful authentication
    @Test
    void authenticate_ValidCredentials_ReturnsUser() {
        // Arrange
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("john.doe@example.com");
        loginUserDto.setPassword("password123");

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        // Act
        User user = authenticationServiceimpl.authenticate(loginUserDto);

        // Assert
        assertNotNull(user);
    }

    // Test for authentication with invalid credentials
    @Test
    void authenticate_InvalidCredentials_ThrowsAuthenticationFailedException() {
        // Arrange
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmail("john.doe@example.com");
        loginUserDto.setPassword("wrongPassword");

        doThrow(new AuthenticationFailedException("Invalid email or password"))
                .when(authenticationManager).authenticate(any());

        // Act & Assert
        assertThrows(AuthenticationFailedException.class, () -> authenticationServiceimpl.authenticate(loginUserDto));
    }
}