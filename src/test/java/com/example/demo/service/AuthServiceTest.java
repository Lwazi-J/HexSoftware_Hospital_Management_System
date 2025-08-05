package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.exception.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;

    private LoginRequest validLoginRequest;
    private LoginRequest invalidLoginRequest;

    @BeforeEach
    void setUp() {
        authService = new AuthService();
        
        validLoginRequest = new LoginRequest();
        validLoginRequest.setUsername("admin");
        validLoginRequest.setPassword("admin123");

        invalidLoginRequest = new LoginRequest();
        invalidLoginRequest.setUsername("invalid");
        invalidLoginRequest.setPassword("invalid");
    }

    @Test
    void authenticateUser_WithValidCredentials_ShouldReturnLoginResponse() {
        LoginResponse response = authService.authenticateUser(validLoginRequest);

        assertNotNull(response);
        assertEquals("jwt-token-123", response.getToken());
        assertEquals("ADMIN", response.getRole());
        assertEquals(1L, response.getUserId());
        assertEquals("Admin User", response.getFullName());
    }

    @Test
    void authenticateUser_WithInvalidCredentials_ShouldThrowUnauthorizedException() {
        assertThrows(UnauthorizedException.class, () -> {
            authService.authenticateUser(invalidLoginRequest);
        });
    }

    @Test
    void authenticateUser_WithNullUsername_ShouldThrowUnauthorizedException() {
        LoginRequest nullUsernameRequest = new LoginRequest();
        nullUsernameRequest.setUsername(null);
        nullUsernameRequest.setPassword("admin123");

        assertThrows(UnauthorizedException.class, () -> {
            authService.authenticateUser(nullUsernameRequest);
        });
    }

    @Test
    void authenticateUser_WithNullPassword_ShouldThrowUnauthorizedException() {
        LoginRequest nullPasswordRequest = new LoginRequest();
        nullPasswordRequest.setUsername("admin");
        nullPasswordRequest.setPassword(null);

        assertThrows(UnauthorizedException.class, () -> {
            authService.authenticateUser(nullPasswordRequest);
        });
    }

    @Test
    void authenticateUser_WithEmptyCredentials_ShouldThrowUnauthorizedException() {
        LoginRequest emptyCredentialsRequest = new LoginRequest();
        emptyCredentialsRequest.setUsername("");
        emptyCredentialsRequest.setPassword("");

        assertThrows(UnauthorizedException.class, () -> {
            authService.authenticateUser(emptyCredentialsRequest);
        });
    }
}