package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.Staff;
import com.example.demo.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        // Hardcoded credentials for testing
        if ("admin".equals(loginRequest.getUsername()) && "admin123".equals(loginRequest.getPassword())) {
            return new LoginResponse(
                    "jwt-token-123",
                    "ADMIN",
                    1L,
                    "Admin User"
            );
        }
        
        throw new UnauthorizedException("Invalid username or password");
    }
}