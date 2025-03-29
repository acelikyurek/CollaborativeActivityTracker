package com.example.spring_projects.collaborativeactivitytracker.controller;

import com.example.spring_projects.collaborativeactivitytracker.request.ActivityCreateRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityUpdateRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.ActivityReadListResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.ActivityReadResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.GenericResponse;
import com.example.spring_projects.collaborativeactivitytracker.service.ActivityServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The controller for managing activities.
 */
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api")
public class ActivityController {

    private final ActivityServiceImpl activityServiceImpl;

    /**
     * Endpoint for creating a new activity.
     *
     * @param activityCreateRequest the request containing activity creation details
     * @return a response entity with the result of the operation
     */
    @PostMapping("/activity")
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
     * @param activityId the request containing the activity ID
     * @return a response entity with the activity details
     */
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<ActivityReadResponse> getActivity(@PathVariable Long activityId) {
        ActivityReadResponse activityReadResponse = activityServiceImpl.getActivity(activityId);
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
    @GetMapping("/activity")
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
     * @param activityId the request containing the activity ID
     * @param activityUpdateRequest the request containing updated activity details
     * @return a response entity with the result of the operation
     */
    @PutMapping("/activity/{activityId}")
    public ResponseEntity<GenericResponse> updateActivity(@PathVariable Long activityId, @RequestBody ActivityUpdateRequest activityUpdateRequest) {
        GenericResponse genericResponse = activityServiceImpl.updateActivity(activityId, activityUpdateRequest);
        if (genericResponse.getOperationSuccessful()) {
            return ResponseEntity.ok().body(genericResponse);
        }
        return ResponseEntity.badRequest().body(genericResponse);
    }

    /**
     * Endpoint for deleting an activity by ID.
     *
     * @param activityId the request containing the activity ID
     * @return a response entity with the result of the deletion operation
     */
    @DeleteMapping("/activity/{activityId}")
    public ResponseEntity<GenericResponse> deleteActivity(@PathVariable Long activityId) {
        GenericResponse genericResponse = activityServiceImpl.deleteActivity(activityId);
        if (genericResponse.getOperationSuccessful()) {
            return ResponseEntity.ok().body(genericResponse);
        }
        return ResponseEntity.badRequest().body(genericResponse);
    }
}
