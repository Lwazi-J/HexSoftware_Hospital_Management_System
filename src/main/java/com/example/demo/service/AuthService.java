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
        Staff staff = staffRepository.findByEmail(loginRequest.getUsername());

        if (staff == null || !passwordEncoder.matches(loginRequest.getPassword(), staff.getPassword())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        // In a real application, generate a proper JWT token here
        String token = "generated-jwt-token";

        return new LoginResponse(
                token,
                staff.getRole(),
                staff.getStaffId(),
                staff.getFirstName() + " " + staff.getLastName()
        );
    }
}