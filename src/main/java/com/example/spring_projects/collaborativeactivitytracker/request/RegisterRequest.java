package com.example.spring_projects.collaborativeactivitytracker.request;

import lombok.*;
import jakarta.validation.constraints.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends GenericRequest {
    @NotNull
    @NotBlank
    @Email
    public String email;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 24)
    public String password;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 64)
    public String name;
}
