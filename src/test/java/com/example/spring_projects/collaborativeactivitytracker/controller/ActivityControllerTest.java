package com.example.spring_projects.collaborativeactivitytracker.controller;

import com.example.spring_projects.collaborativeactivitytracker.request.ActivityCreateRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityUpdateRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.ActivityReadListResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.ActivityReadResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.GenericResponse;
import com.example.spring_projects.collaborativeactivitytracker.service.ActivityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityControllerTest {

    @InjectMocks
    private ActivityController activityController;

    @Mock
    private ActivityServiceImpl activityServiceImpl;

    @Test
    void testCreateActivity_Success() {
        ActivityCreateRequest activityCreateRequest = new ActivityCreateRequest("Title", "Description", "10.10.2025", 6);
        GenericResponse genericResponse = GenericResponse.builder().operationSuccessful(true).message("Activity created successfully!").build();
        when(activityServiceImpl.createActivity(any(ActivityCreateRequest.class))).thenReturn(genericResponse);
        ResponseEntity<GenericResponse> response = activityController.createActivity(activityCreateRequest);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(genericResponse, response.getBody());
    }

    @Test
    void testCreateActivity_Failure() {
        ActivityCreateRequest activityCreateRequest = new ActivityCreateRequest("Title", "Description", "10.10.2025", 6);
        GenericResponse genericResponse = GenericResponse.builder().operationSuccessful(false).message("Activity creation failed!").build();
        when(activityServiceImpl.createActivity(any(ActivityCreateRequest.class))).thenReturn(genericResponse);
        ResponseEntity<GenericResponse> response = activityController.createActivity(activityCreateRequest);
        assertEquals(400, response.getStatusCode().value());
        assertEquals(genericResponse, response.getBody());
    }

    @Test
    void testGetActivity_Success() {
        Long activityId = 1L;
        ActivityReadResponse activityReadResponse = ActivityReadResponse.builder().operationSuccessful(true).message("Activity retrieved successfully!").activity(null).build();
        when(activityServiceImpl.getActivity(any(Long.class))).thenReturn(activityReadResponse);
        ResponseEntity<ActivityReadResponse> response = activityController.getActivity(activityId);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(activityReadResponse, response.getBody());
    }

    @Test
    void testGetActivity_Failure() {
        Long activityId = 1L;
        ActivityReadResponse activityReadResponse = ActivityReadResponse.builder().operationSuccessful(false).message("Activity not found!").activity(null).build();
        when(activityServiceImpl.getActivity(any(Long.class))).thenReturn(activityReadResponse);
        ResponseEntity<ActivityReadResponse> response = activityController.getActivity(activityId);
        assertEquals(400, response.getStatusCode().value());
        assertEquals(activityReadResponse, response.getBody());
    }

    @Test
    void testGetActivityAll_Success() {
        ActivityReadListResponse activityReadListResponse = ActivityReadListResponse.builder().operationSuccessful(true).message("Activities retrieved successfully!").activityList(null).build();
        when(activityServiceImpl.getActivityList()).thenReturn(activityReadListResponse);
        ResponseEntity<ActivityReadListResponse> response = activityController.getActivityAll();
        assertEquals(200, response.getStatusCode().value());
        assertEquals(activityReadListResponse, response.getBody());
    }

    @Test
    void testGetActivityAll_Failure() {
        ActivityReadListResponse activityReadListResponse = ActivityReadListResponse.builder().operationSuccessful(false).message("No activities found!").activityList(null).build();
        when(activityServiceImpl.getActivityList()).thenReturn(activityReadListResponse);
        ResponseEntity<ActivityReadListResponse> response = activityController.getActivityAll();
        assertEquals(400, response.getStatusCode().value());
        assertEquals(activityReadListResponse, response.getBody());
    }

    @Test
    void testUpdateActivity_Success() {
        Long activityId = 1L;
        ActivityUpdateRequest activityUpdateRequest = new ActivityUpdateRequest(null, "Updated Description", null, null, null, null);
        GenericResponse genericResponse = GenericResponse.builder().operationSuccessful(true).message("Activity updated successfully!").build();
        when(activityServiceImpl.updateActivity(any(Long.class), any(ActivityUpdateRequest.class))).thenReturn(genericResponse);
        ResponseEntity<GenericResponse> response = activityController.updateActivity(activityId, activityUpdateRequest);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(genericResponse, response.getBody());
    }

    @Test
    void testUpdateActivity_Failure() {
        Long activityId = 1L;
        ActivityUpdateRequest activityUpdateRequest = new ActivityUpdateRequest(null, "Updated Description", null, null, null, null);
        GenericResponse genericResponse = GenericResponse.builder().operationSuccessful(false).message("Activity update failed!").build();
        when(activityServiceImpl.updateActivity(any(Long.class), any(ActivityUpdateRequest.class))).thenReturn(genericResponse);
        ResponseEntity<GenericResponse> response = activityController.updateActivity(activityId, activityUpdateRequest);
        assertEquals(400, response.getStatusCode().value());
        assertEquals(genericResponse, response.getBody());
    }

    @Test
    void testDeleteActivity_Success() {
        Long activityId = 1L;
        GenericResponse genericResponse = GenericResponse.builder().operationSuccessful(true).message("Activity deleted successfully!").build();
        when(activityServiceImpl.deleteActivity(any(Long.class))).thenReturn(genericResponse);
        ResponseEntity<GenericResponse> response = activityController.deleteActivity(activityId);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(genericResponse, response.getBody());
    }

    @Test
    void testDeleteActivity_Failure() {
        Long activityId = 1L;
        GenericResponse genericResponse = GenericResponse.builder().operationSuccessful(false).message("Activity deletion failed!").build();
        when(activityServiceImpl.deleteActivity(any(Long.class))).thenReturn(genericResponse);
        ResponseEntity<GenericResponse> response = activityController.deleteActivity(activityId);
        assertEquals(400, response.getStatusCode().value());
        assertEquals(genericResponse, response.getBody());
    }
}
