package com.example.spring_projects.collaborativeactivitytracker.service;

import com.example.spring_projects.collaborativeactivitytracker.request.*;
import com.example.spring_projects.collaborativeactivitytracker.response.*;

public interface ActivityService {
    GenericResponse createActivity(ActivityCreateRequest activityCreateRequest);
    ActivityReadResponse getActivity(ActivityGenericRequest activityGenericRequest);
    ActivityReadListResponse getActivityList();
    GenericResponse updateActivity(ActivityUpdateRequest activityUpdateRequest);
    GenericResponse deleteActivity(ActivityGenericRequest activityGenericRequest);
}