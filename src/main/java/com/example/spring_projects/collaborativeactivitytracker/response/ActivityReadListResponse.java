package com.example.spring_projects.collaborativeactivitytracker.response;

import com.example.spring_projects.collaborativeactivitytracker.model.Activity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ActivityReadListResponse extends GenericResponse {
    public List<Activity> activityList;
}
