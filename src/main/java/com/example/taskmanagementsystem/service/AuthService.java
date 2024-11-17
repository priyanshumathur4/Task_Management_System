package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.dto.Login;
import com.example.taskmanagementsystem.dto.Register;
import com.example.taskmanagementsystem.model.User;
import com.example.taskmanagementsystem.security.AuthResponse;

import java.util.Optional;

public interface AuthService {
    AuthResponse register(Register register);
    Optional<User> login(Login login);
}