package com.example.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskmanagementsystem.dto.Login;
import com.example.taskmanagementsystem.dto.Register;
import com.example.taskmanagementsystem.security.AuthResponse;
import com.example.taskmanagementsystem.service.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody Register register) {
        AuthResponse response = authService.register(register);
        return ResponseEntity.status(201).body(response); // Return 201 Created
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody Login login) {
        AuthResponse response = authService.login(login);
        return ResponseEntity.ok(response); // Return 200 OK
    }
}