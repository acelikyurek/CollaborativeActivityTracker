package com.example.spring_projects.collaborativeactivitytracker.repository;

import com.example.spring_projects.collaborativeactivitytracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
