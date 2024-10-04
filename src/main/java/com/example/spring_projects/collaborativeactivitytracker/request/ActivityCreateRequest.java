package com.example.spring_projects.collaborativeactivitytracker.request;

import jakarta.validation.constraints.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityCreateRequest extends GenericRequest {

    @NotNull
    @NotBlank
    public String activityTitle;

    @NotNull
    @NotBlank
    public String activityDescription;

    @NotNull
    @NotBlank
    public String plannedAt; // DD.MM.YYYY

    @NotNull
    @NotBlank
    public Integer maximumParticipants;
}