package com.example.spring_projects.collaborativeactivitytracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NonNull
    @Email
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String name;

}