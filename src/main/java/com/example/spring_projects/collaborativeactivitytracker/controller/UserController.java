package com.example.spring_projects.collaborativeactivitytracker.controller;

import com.example.spring_projects.collaborativeactivitytracker.request.LoginRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.RegisterRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.LoginResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.RegisterResponse;
import com.example.spring_projects.collaborativeactivitytracker.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for managing user-related actions such as login and registration.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    /**
     * Endpoint for user registration.
     *
     * @param registerRequest the request containing registration details (username, email, password)
     * @return a response entity with the registration result
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse registerResponse = userServiceImpl.register(registerRequest);
        if (registerResponse.registerSuccessful) {
            return ResponseEntity.ok().body(registerResponse);
        }
        return ResponseEntity.badRequest().body(registerResponse);
    }

    /**
     * Endpoint for user login.
     *
     * @param loginRequest the request containing login details (username and password)
     * @return a response entity with the login result
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userServiceImpl.login(loginRequest);
        if (loginResponse.loginSuccessful) {
            return ResponseEntity.ok().body(loginResponse);
        }
        return ResponseEntity.badRequest().body(loginResponse);
    }
}