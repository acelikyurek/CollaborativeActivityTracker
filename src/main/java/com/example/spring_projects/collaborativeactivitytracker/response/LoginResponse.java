package com.example.spring_projects.collaborativeactivitytracker.response;

import lombok.*;
import jakarta.validation.constraints.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class LoginResponse {
    @NotNull
    @NotBlank
    public Boolean loginSuccessful;
    public String message;
    public String tokenString;
}