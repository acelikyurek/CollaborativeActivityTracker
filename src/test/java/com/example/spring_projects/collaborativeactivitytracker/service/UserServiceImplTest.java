package com.example.spring_projects.collaborativeactivitytracker.service;

import com.example.spring_projects.collaborativeactivitytracker.model.User;
import com.example.spring_projects.collaborativeactivitytracker.repository.UserRepository;
import com.example.spring_projects.collaborativeactivitytracker.request.LoginRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.RegisterRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.LoginResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.RegisterResponse;
import com.example.spring_projects.collaborativeactivitytracker.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void testRegister_Success() {
        final String email = "johndoe@domain.com";
        final String password = "password";
        final String name = "John Doe";
        RegisterRequest registerRequest = new RegisterRequest(email, password, name);
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        RegisterResponse registerResponse = userServiceImpl.register(registerRequest);
        assertTrue(registerResponse.getRegisterSuccessful());
        assertEquals("Register successful!", registerResponse.getMessage());
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(registerRequest.getPassword());
    }

    @Test
    public void testRegister_IncorrectEmailFormat() {
        final String email = "incorrect-email-format";
        final String password = "password";
        final String name = "John Doe";
        RegisterRequest registerRequest = new RegisterRequest(email, password, name);
        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        doThrow(new RuntimeException()).when(userRepository).save(any(User.class));
        RegisterResponse registerResponse = userServiceImpl.register(registerRequest);
        assertFalse(registerResponse.getRegisterSuccessful());
        assertEquals("A problem occurred!", registerResponse.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegister_UserAlreadyExists() {
        final String email = "johndoe@domain.com";
        final String password = "password";
        final String name = "John Doe";
        RegisterRequest registerRequest = new RegisterRequest(email, password, name);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));
        RegisterResponse registerResponse = userServiceImpl.register(registerRequest);
        assertFalse(registerResponse.getRegisterSuccessful());
        assertEquals("User already exists!", registerResponse.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLogin_Success() {
        final String email = "johndoe@domain.com";
        final String password = "password";
        final String encodedPassword = "encodedPassword";
        final String name = "John Doe";
        final String mockedToken = "mockedToken";
        LoginRequest loginRequest = new LoginRequest(email, password);
        User user = new User(email, encodedPassword, name);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtTokenProvider.generateToken(loginRequest.getEmail())).thenReturn(mockedToken);
        LoginResponse loginResponse = userServiceImpl.login(loginRequest);
        assertTrue(loginResponse.getLoginSuccessful());
        assertEquals("Login successful!", loginResponse.getMessage());
        assertEquals(mockedToken, loginResponse.getTokenString());
    }

    @Test
    public void testLogin_UserNotFound() {
        final String email = "johndoe@domain.com";
        final String password = "password";
        final String mockedToken = "";
        LoginRequest loginRequest = new LoginRequest(email, password);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());
        LoginResponse response = userServiceImpl.login(loginRequest);
        assertFalse(response.getLoginSuccessful());
        assertEquals("User not found!", response.getMessage());
        assertEquals(mockedToken, response.getTokenString());
    }

    @Test
    public void testLogin_IncorrectPassword() {
        final String email = "johndoe@domain.com";
        final String password = "wrongPassword";
        final String encodedPassword = "encodedPassword";
        final String name = "John Doe";
        final String mockedToken = "";
        LoginRequest loginRequest = new LoginRequest(email, password);
        User user = new User(email, encodedPassword, name);
        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);
        LoginResponse response = userServiceImpl.login(loginRequest);
        assertFalse(response.getLoginSuccessful());
        assertEquals("Password is incorrect!", response.getMessage());
        assertEquals(mockedToken, response.getTokenString());
    }
}