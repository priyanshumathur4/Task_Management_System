package com.example.taskmanagementsystem.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.taskmanagementsystem.dto.Login;
import com.example.taskmanagementsystem.dto.Register;
import com.example.taskmanagementsystem.exception.ApiException;
import com.example.taskmanagementsystem.model.User;
import com.example.taskmanagementsystem.repository.UserRepository;
import com.example.taskmanagementsystem.security.AuthResponse;
import com.example.taskmanagementsystem.utils.JwtUtil;
import com.example.taskmanagementsystem.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse register(Register register) {
        if (userRepository.existsByEmail(register.getEmail()))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Email has been already used.");
        if (userRepository.existsByUsername(register.getUsername()))
            throw new ApiException(HttpStatus.BAD_REQUEST, "Username already taken.");

        User user = buildUser(register);
        User savedUser = userRepository.save(user);

        // Optionally generate a JWT for the new user
        String token = jwtUtil.generateToken(savedUser.getUsername());

        return new AuthResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getRole(), token);
    }


    @Override
    public AuthResponse login(Login login) {
        return userRepository.findByUsernameOrEmail(login.getUsername(), login.getUsername())
                .filter(user -> passwordEncoder.matches(login.getPassword(), user.getPassword()))
                .map(user -> {
                    String token = jwtUtil.generateToken(user.getUsername());
                    return new AuthResponse(user.getId(), user.getUsername(), user.getRole(), token);
                })
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
    }

    private User buildUser(Register register) {
        User user = new User();
        user.setEmail(register.getEmail());
        user.setUsername(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        user.setRole("USER");
        return user;
    }
}