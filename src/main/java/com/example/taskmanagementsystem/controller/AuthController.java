package com.example.taskmanagementsystem.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskmanagementsystem.dto.Login;
import com.example.taskmanagementsystem.dto.Register;
import com.example.taskmanagementsystem.model.User;
import com.example.taskmanagementsystem.security.AuthResponse;
import com.example.taskmanagementsystem.service.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody Register register){
        return ResponseEntity.ok(authService.register(register));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody Login login){
        Optional<User> user = authService.login(login);
        if (user.isPresent()){
            User presentUser = user.get();
            return ResponseEntity.ok(new AuthResponse(presentUser.getId(), presentUser.getUsername(), presentUser.getRole()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}