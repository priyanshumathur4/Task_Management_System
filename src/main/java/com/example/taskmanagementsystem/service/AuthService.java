package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.dto.Login;
import com.example.taskmanagementsystem.dto.Register;
import com.example.taskmanagementsystem.security.AuthResponse;

public interface AuthService {
    AuthResponse register(Register register);
    AuthResponse login(Login login);
}