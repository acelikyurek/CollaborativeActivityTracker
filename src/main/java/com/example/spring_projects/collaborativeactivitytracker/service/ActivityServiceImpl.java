package com.example.spring_projects.collaborativeactivitytracker.service;

import com.example.spring_projects.collaborativeactivitytracker.model.*;
import com.example.spring_projects.collaborativeactivitytracker.repository.ActivityRepository;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityCreateRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityGenericRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityUpdateRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing activities.
 */
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;

    /**
     * Creates a new activity.
     *
     * @param activityCreateRequest the request containing activity details
     * @return a response indicating whether the operation was successful or not
     */
    public GenericResponse createActivity(ActivityCreateRequest activityCreateRequest) {
        Date currentDate = new Date();
        Date plannedAtDate;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            plannedAtDate = formatter.parse(activityCreateRequest.getPlannedAt());
        } catch (ParseException parseException) {
            return GenericResponse.builder().operationSuccessful(false).message("Activity date is not in correct format!").build();
        }
        if (plannedAtDate.before(currentDate)) {
            return GenericResponse.builder().operationSuccessful(false).message("Activity cannot be before from the current date!").build();
        }
        if (activityCreateRequest.getMaximumParticipants() < 2) {
            return GenericResponse.builder().operationSuccessful(false).message("Number of maximum participants must be larger than 2!").build();
        }
        Activity activity = new Activity(
            activityCreateRequest.getActivityTitle(),
            activityCreateRequest.getActivityDescription(),
            currentDate,
            currentDate,
            plannedAtDate,
            1,
            activityCreateRequest.getMaximumParticipants(),
            false
        );
        activityRepository.save(activity);
        return GenericResponse.builder().operationSuccessful(true).message("Operation successful!").build();
    }

    /**
     * Retrieves the details of an activity based on the activity ID.
     *
     * @param activityGenericRequest the request containing the activity ID
     * @return a response containing the activity details if found
     */
    public ActivityReadResponse getActivity(ActivityGenericRequest activityGenericRequest) {
        Optional<Activity> activityOptional = activityRepository.findById(activityGenericRequest.getActivityId());
        if (activityOptional.isEmpty()) {
            return ActivityReadResponse.builder().operationSuccessful(false).message("Activity not found!").build();
        }
        return ActivityReadResponse.builder().operationSuccessful(true).message("Operation successful!").activity(activityOptional.get()).build();
    }

    /**
     * Retrieves a list of all activities.
     *
     * @return a response containing the list of all activities
     */
    public ActivityReadListResponse getActivityList() {
        List<Activity> activities = activityRepository.findAll();
        return ActivityReadListResponse.builder().operationSuccessful(true).message("Operation successful!").activityList(activities).build();
    }

    /**
     * Updates an existing activity based on the request details.
     *
     * @param activityUpdateRequest the request containing updated activity details
     * @return a response indicating whether the operation was successful or not
     */
    public GenericResponse updateActivity(ActivityUpdateRequest activityUpdateRequest) {
        if (activityUpdateRequest.getActivityTitle() == null && activityUpdateRequest.getActivityDescription() == null && activityUpdateRequest.getPlannedAt() == null && activityUpdateRequest.getCurrentParticipants() == null && activityUpdateRequest.getMaximumParticipants() == null && activityUpdateRequest.getIsCompleted() == null) {
            return GenericResponse.builder().operationSuccessful(false).message("No update is requested!").build();
        }
        Optional<Activity> activityOptional = activityRepository.getActivityByActivityId(activityUpdateRequest.getActivityId());
        if (activityOptional.isEmpty()) {
            return GenericResponse.builder().operationSuccessful(false).message("Activity not found!").build();
        }
        Activity activity = activityOptional.get();
        Date currentDate = new Date();
        activity.setLastEditedAt(currentDate);
        if (activityUpdateRequest.getActivityTitle() != null) {
            activity.setActivityTitle(activityUpdateRequest.getActivityTitle());
        }
        if (activityUpdateRequest.getActivityDescription() != null) {
            activity.setActivityDescription(activityUpdateRequest.getActivityDescription());
        }
        if (activityUpdateRequest.getPlannedAt() != null) {
            Date plannedAtDate;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                plannedAtDate = formatter.parse(activityUpdateRequest.getPlannedAt());
            } catch (ParseException parseException) {
                return GenericResponse.builder().operationSuccessful(false).message(parseException.getMessage()).build();
            }
            if (plannedAtDate.before(currentDate)) {
                return GenericResponse.builder().operationSuccessful(false).message("Activity cannot be before from the current date!").build();
            }
            activity.setPlannedAt(plannedAtDate);
        }
        if (activityUpdateRequest.getCurrentParticipants() != null && activityUpdateRequest.getMaximumParticipants() != null) {
            if (activityUpdateRequest.getMaximumParticipants() < 2) {
                return GenericResponse.builder().operationSuccessful(false).message("Number of maximum participants must be at least 2!").build();
            }
            if (activityUpdateRequest.getMaximumParticipants() < activityUpdateRequest.getCurrentParticipants()) {
                return GenericResponse.builder().operationSuccessful(false).message("Number of maximum participants cannot be smaller than number of current participants!").build();
            }
            activity.setCurrentParticipants(activityUpdateRequest.getCurrentParticipants());
            activity.setMaximumParticipants(activityUpdateRequest.getMaximumParticipants());
        } else if (activityUpdateRequest.getCurrentParticipants() != null && activityUpdateRequest.getMaximumParticipants() == null) {
            if (activity.getMaximumParticipants() < activityUpdateRequest.getCurrentParticipants()) {
                return GenericResponse.builder().operationSuccessful(false).message("Number of maximum participants cannot be smaller than number of current participants!").build();
            }
            activity.setCurrentParticipants(activityUpdateRequest.getCurrentParticipants());
        } else if (activityUpdateRequest.getCurrentParticipants() == null && activityUpdateRequest.getMaximumParticipants() != null) {
            if (activityUpdateRequest.getMaximumParticipants() < 2) {
                return GenericResponse.builder().operationSuccessful(false).message("Number of maximum participants must be at least 2!").build();
            }
            if (activityUpdateRequest.getMaximumParticipants() < activity.getCurrentParticipants()) {
                return GenericResponse.builder().operationSuccessful(false).message("Number of maximum participants cannot be smaller than number of current participants!").build();
            }
            activity.setMaximumParticipants(activity.getMaximumParticipants());
        }
        if (activityUpdateRequest.getIsCompleted() != null) {
            activity.setIsCompleted(activityUpdateRequest.getIsCompleted());
        }
        activityRepository.save(activity);
        return GenericResponse.builder().operationSuccessful(true).message("Operation successful!").build();
    }

    /**
     * Deletes an activity based on the activity ID.
     *
     * @param activityGenericRequest the request containing the activity ID
     * @return a response indicating whether the operation was successful or not
     */
    public GenericResponse deleteActivity(ActivityGenericRequest activityGenericRequest) {
        Optional<Activity> activityOptional = activityRepository.findById(activityGenericRequest.getActivityId());
        if (activityOptional.isEmpty()) {
            return ActivityReadResponse.builder().operationSuccessful(false).message("Activity not found!").build();
        }
        Activity activity = activityOptional.get();
        activityRepository.delete(activity);
        return GenericResponse.builder().operationSuccessful(true).message("Operation successful!").build();
    }
}
