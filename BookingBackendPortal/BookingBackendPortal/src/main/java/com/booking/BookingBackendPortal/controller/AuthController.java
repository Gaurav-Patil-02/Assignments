package com.booking.BookingBackendPortal.controller;

import com.booking.BookingBackendPortal.dto.JwtResponse;
import com.booking.BookingBackendPortal.dto.LoginRequest;
import com.booking.BookingBackendPortal.dto.RegisterRequest;
import com.booking.BookingBackendPortal.entity.Role;
import com.booking.BookingBackendPortal.entity.User;
import com.booking.BookingBackendPortal.repository.UserRepository;
import com.booking.BookingBackendPortal.service.AuthService;
import com.booking.BookingBackendPortal.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        //.map(role -> role.name())

        return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getUsername(), roles));
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        // Encode password
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User user = new User(registerRequest.getUsername(), encodedPassword);

        // Default role if none provided
        if (registerRequest.getRoles() == null || registerRequest.getRoles().isEmpty()) {
            user.getRoles().add(Role.ROLE_USER);
        } else {
            for (String roleStr : registerRequest.getRoles()) {
                if (roleStr.equalsIgnoreCase("ROLE_ADMIN")) {
                    user.getRoles().add(Role.ROLE_ADMIN);
                } else {
                    user.getRoles().add(Role.ROLE_USER);
                }
            }
        }

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

}