package com.example.spring_projects.collaborativeactivitytracker.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @NonNull
    private String activityTitle;

    @NonNull
    private String activityDescription;

    @NonNull
    private Date createdAt;

    @NonNull
    private Date lastEditedAt;

    @NonNull
    private Date plannedAt;

    @NonNull
    private Integer currentParticipants;

    @NonNull
    private Integer maximumParticipants;

    @NonNull
    private Boolean isCompleted;

}