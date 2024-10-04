package com.example.spring_projects.collaborativeactivitytracker.service;

import com.example.spring_projects.collaborativeactivitytracker.request.LoginRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.RegisterRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.LoginResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.RegisterResponse;

public interface UserService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
