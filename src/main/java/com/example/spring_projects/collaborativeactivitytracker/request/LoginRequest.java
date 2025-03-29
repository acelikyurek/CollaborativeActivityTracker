package com.example.spring_projects.collaborativeactivitytracker.request;

import jakarta.validation.constraints.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest extends GenericRequest {

    @NotNull
    @NotBlank
    @Email
    public String email;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 24)
    public String password;
}
