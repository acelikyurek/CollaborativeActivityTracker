package com.example.spring_projects.collaborativeactivitytracker.response;

import com.example.spring_projects.collaborativeactivitytracker.model.Activity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ActivityReadResponse extends GenericResponse {
    public Activity activity;
}
