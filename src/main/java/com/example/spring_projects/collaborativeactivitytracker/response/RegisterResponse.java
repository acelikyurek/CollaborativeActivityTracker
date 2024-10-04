package com.example.spring_projects.collaborativeactivitytracker.response;

import lombok.*;
import jakarta.validation.constraints.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class RegisterResponse {
    @NotNull
    @NotBlank
    public Boolean registerSuccessful;
    public String message;
}
