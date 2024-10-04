package com.example.spring_projects.collaborativeactivitytracker.request;

import jakarta.validation.constraints.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityGenericRequest extends GenericRequest {

    @NotNull
    @NotBlank
    public Long activityId;
}