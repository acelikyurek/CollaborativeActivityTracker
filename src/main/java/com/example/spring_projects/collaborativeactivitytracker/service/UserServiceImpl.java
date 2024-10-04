package com.example.spring_projects.collaborativeactivitytracker.service;

import com.example.spring_projects.collaborativeactivitytracker.model.User;
import com.example.spring_projects.collaborativeactivitytracker.repository.UserRepository;
import com.example.spring_projects.collaborativeactivitytracker.request.LoginRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.RegisterRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.LoginResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.RegisterResponse;
import com.example.spring_projects.collaborativeactivitytracker.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Represents a service for managing user registrations and logins.
 * Provides methods for user registration and authentication.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Registers a new user.
     *
     * @param registerRequest the request containing user registration details
     * @return a response indicating the success or failure of the registration and a message
     */
    public RegisterResponse register(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            return RegisterResponse.builder().registerSuccessful(false).message("User already exists!").build();
        }
        User user = new User(registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getName());
        try {
            userRepository.save(user);
        } catch (RuntimeException runtimeException) {
            return RegisterResponse.builder().registerSuccessful(false).message("A problem occurred!").build();
        }
        return RegisterResponse.builder().registerSuccessful(true).message("Register successful!").build();
    }

    /**
     * Logins the existing user.
     *
     * @param loginRequest the request containing user authentication details
     * @return a response indicating the success or failure of the authentication and a message
     */
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> existingUser = userRepository.findByEmail(loginRequest.getEmail());
        if (existingUser.isEmpty()) {
            return LoginResponse.builder().loginSuccessful(false).message("User not found!").tokenString("").build();
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), existingUser.get().getPassword())) {
            return LoginResponse.builder().loginSuccessful(false).message("Password is incorrect!").tokenString("").build();
        }
        String tokenString = jwtTokenProvider.generateToken(loginRequest.getEmail());
        return LoginResponse.builder().loginSuccessful(true).message("Login successful!").tokenString(tokenString).build();
    }
}
