package com.example.spring_projects.collaborativeactivitytracker.repository;

import com.example.spring_projects.collaborativeactivitytracker.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> getActivityByActivityId(Long activityId);
}
