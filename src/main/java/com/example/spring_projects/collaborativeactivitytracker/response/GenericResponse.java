package com.example.spring_projects.collaborativeactivitytracker.response;

import lombok.*;
import jakarta.validation.constraints.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class GenericResponse {
    @NotNull
    @NotBlank
    public Boolean operationSuccessful;
    public String message;
}
