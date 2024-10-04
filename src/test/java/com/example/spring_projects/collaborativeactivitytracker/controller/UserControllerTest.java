package com.example.spring_projects.collaborativeactivitytracker.controller;

import com.example.spring_projects.collaborativeactivitytracker.request.LoginRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.RegisterRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.LoginResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.RegisterResponse;
import com.example.spring_projects.collaborativeactivitytracker.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Test
    void testRegister_Success() {
        final String email = "johndoe@domain.com";
        final String password = "password";
        final String name = "John Doe";
        RegisterRequest registerRequest = new RegisterRequest(email, password, name);
        RegisterResponse registerResponse = RegisterResponse.builder().registerSuccessful(true).message("Registration successful!").build();
        when(userServiceImpl.register(any(RegisterRequest.class))).thenReturn(registerResponse);
        ResponseEntity<RegisterResponse> response = userController.register(registerRequest);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(registerResponse, response.getBody());
    }

    @Test
    void testRegister_Failure() {
        final String email = "johndoe@domain.com";
        final String password = "password";
        final String name = "John Doe";
        RegisterRequest registerRequest = new RegisterRequest(email, password, name);
        RegisterResponse registerResponse = RegisterResponse.builder().registerSuccessful(false).message("Registration failed!").build();
        when(userServiceImpl.register(any(RegisterRequest.class))).thenReturn(registerResponse);
        ResponseEntity<RegisterResponse> response = userController.register(registerRequest);
        assertEquals(400, response.getStatusCode().value());
        assertEquals(registerResponse, response.getBody());
    }

    @Test
    void testLogin_Success() {
        final String email = "johndoe@domain.com";
        final String password = "password";
        LoginRequest loginRequest = new LoginRequest(email, password);
        LoginResponse loginResponse = LoginResponse.builder().loginSuccessful(true).message("Login successful!").build();
        when(userServiceImpl.login(any(LoginRequest.class))).thenReturn(loginResponse);
        ResponseEntity<LoginResponse> response = userController.login(loginRequest);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(loginResponse, response.getBody());
    }

    @Test
    void testLogin_Failure() {
        final String email = "johndoe@domain.com";
        final String password = "wrongPassword";
        LoginRequest loginRequest = new LoginRequest(email, password);
        LoginResponse loginResponse = LoginResponse.builder().loginSuccessful(false).message("Login failed!").build();
        when(userServiceImpl.login(any(LoginRequest.class))).thenReturn(loginResponse);
        ResponseEntity<LoginResponse> response = userController.login(loginRequest);
        assertEquals(400, response.getStatusCode().value());
        assertEquals(loginResponse, response.getBody());
    }
}
