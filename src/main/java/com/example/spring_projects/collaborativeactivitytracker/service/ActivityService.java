package com.example.spring_projects.collaborativeactivitytracker.service;

import com.example.spring_projects.collaborativeactivitytracker.request.*;
import com.example.spring_projects.collaborativeactivitytracker.response.*;

public interface ActivityService {
    GenericResponse createActivity(ActivityCreateRequest activityCreateRequest);
    ActivityReadResponse getActivity(Long activityId);
    ActivityReadListResponse getActivityList();
    GenericResponse updateActivity(Long activityId, ActivityUpdateRequest activityUpdateRequest);
    GenericResponse deleteActivity(Long activityId);
}
