package com.example.spring_projects.collaborativeactivitytracker.request;

import jakarta.validation.constraints.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityUpdateRequest extends GenericRequest {

    @NotNull
    @NotBlank
    public Long activityId;
    public String activityTitle;
    public String activityDescription;
    public String plannedAt; // DD.MM.YYYY
    public Integer currentParticipants;
    public Integer maximumParticipants;
    public Boolean isCompleted;
}
