package com.example.spring_projects.collaborativeactivitytracker.controller;

import com.example.spring_projects.collaborativeactivitytracker.request.ActivityCreateRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityGenericRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityUpdateRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.ActivityReadListResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.ActivityReadResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.GenericResponse;
import com.example.spring_projects.collaborativeactivitytracker.service.ActivityServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for managing activities.
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/activity")
public class ActivityController {

    private final ActivityServiceImpl activityServiceImpl;

    /**
     * Endpoint for creating a new activity.
     *
     * @param activityCreateRequest the request containing activity creation details
     * @return a response entity with the result of the operation
     */
    @PostMapping("/createActivity")
    public ResponseEntity<GenericResponse> createActivity(ActivityCreateRequest activityCreateRequest) {
        GenericResponse genericResponse = activityServiceImpl.createActivity(activityCreateRequest);
        if (genericResponse.getOperationSuccessful()) {
            return ResponseEntity.ok().body(genericResponse);
        }
        return ResponseEntity.badRequest().body(genericResponse);
    }

    /**
     * Endpoint for retrieving a specific activity by ID.
     *
     * @param activityGenericRequest the request containing the activity ID
     * @return a response entity with the activity details
     */
    @PostMapping("/getActivity")
    public ResponseEntity<ActivityReadResponse> getActivity(@RequestBody ActivityGenericRequest activityGenericRequest) {
        ActivityReadResponse activityReadResponse = activityServiceImpl.getActivity(activityGenericRequest);
        if (activityReadResponse.getOperationSuccessful()) {
            return ResponseEntity.ok().body(activityReadResponse);
        }
        return ResponseEntity.badRequest().body(activityReadResponse);
    }

    /**
     * Endpoint for retrieving a list of all activities.
     *
     * @return a response entity with the list of activities
     */
    @PostMapping("/getActivityAll")
    public ResponseEntity<ActivityReadListResponse> getActivityAll() {
        ActivityReadListResponse activityReadListResponse = activityServiceImpl.getActivityList();
        if (activityReadListResponse.getOperationSuccessful()) {
            return ResponseEntity.ok().body(activityReadListResponse);
        }
        return ResponseEntity.badRequest().body(activityReadListResponse);
    }

    /**
     * Endpoint for updating an existing activity.
     *
     * @param activityUpdateRequest the request containing updated activity details
     * @return a response entity with the result of the operation
     */
    @PostMapping("/updateActivity")
    public ResponseEntity<GenericResponse> updateActivity(ActivityUpdateRequest activityUpdateRequest) {
        GenericResponse genericResponse = activityServiceImpl.updateActivity(activityUpdateRequest);
        if (genericResponse.getOperationSuccessful()) {
            return ResponseEntity.ok().body(genericResponse);
        }
        return ResponseEntity.badRequest().body(genericResponse);
    }

    /**
     * Endpoint for deleting an activity by ID.
     *
     * @param activityGenericRequest the request containing the activity ID
     * @return a response entity with the result of the deletion operation
     */
    @PostMapping("/deleteActivity")
    public ResponseEntity<GenericResponse> deleteActivity(@RequestBody ActivityGenericRequest activityGenericRequest) {
        GenericResponse genericResponse = activityServiceImpl.deleteActivity(activityGenericRequest);
        if (genericResponse.getOperationSuccessful()) {
            return ResponseEntity.ok().body(genericResponse);
        }
        return ResponseEntity.badRequest().body(genericResponse);
    }
}
