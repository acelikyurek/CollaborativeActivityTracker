package com.example.spring_projects.collaborativeactivitytracker.service;

import com.example.spring_projects.collaborativeactivitytracker.model.Activity;
import com.example.spring_projects.collaborativeactivitytracker.repository.ActivityRepository;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityCreateRequest;
import com.example.spring_projects.collaborativeactivitytracker.request.ActivityUpdateRequest;
import com.example.spring_projects.collaborativeactivitytracker.response.ActivityReadListResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.ActivityReadResponse;
import com.example.spring_projects.collaborativeactivitytracker.response.GenericResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplTest {

    @InjectMocks
    private ActivityServiceImpl activityServiceImpl;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    void testCreateActivity_Success() {
        final String activityTitle = "Activity Title";
        final String activityDescription = "Activity Description";
        final String activityDate = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        final Integer maximumParticipants = 5;
        ActivityCreateRequest activityCreateRequest = new ActivityCreateRequest(activityTitle, activityDescription, activityDate, maximumParticipants);
        GenericResponse genericResponse = activityServiceImpl.createActivity(activityCreateRequest);
        assertTrue(genericResponse.getOperationSuccessful());
        assertEquals("Operation successful!", genericResponse.getMessage());
        ArgumentCaptor<Activity> activityCaptor = ArgumentCaptor.forClass(Activity.class);
        verify(activityRepository).save(activityCaptor.capture());
        Activity savedActivity = activityCaptor.getValue();
        assertEquals(activityTitle, savedActivity.getActivityTitle());
        assertEquals(activityDescription, savedActivity.getActivityDescription());
        try {
            assertEquals(new SimpleDateFormat("dd.MM.yyyy").parse(activityDate), savedActivity.getPlannedAt());
        } catch (ParseException parseException) {
            throw new RuntimeException(parseException);
        }
        assertEquals(maximumParticipants, savedActivity.getMaximumParticipants());
    }

    @Test
    void testCreateActivity_InvalidDateFormat() {
        final String activityTitle = "Activity Title";
        final String activityDescription = "Activity Description";
        final String activityDate = "invalid date";
        final Integer maximumParticipants = 5;
        ActivityCreateRequest activityCreateRequest = new ActivityCreateRequest(activityTitle, activityDescription, activityDate, maximumParticipants);
        GenericResponse genericResponse = activityServiceImpl.createActivity(activityCreateRequest);
        assertFalse(genericResponse.getOperationSuccessful());
        assertEquals("Activity date is not in correct format!", genericResponse.getMessage());
    }

    @Test
    void testCreateActivity_DateInPast() {
        final String activityTitle = "Activity Title";
        final String activityDescription = "Activity Description";
        final String activityDate = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        final Integer maximumParticipants = 5;
        ActivityCreateRequest activityCreateRequest = new ActivityCreateRequest(activityTitle, activityDescription, activityDate, maximumParticipants);
        GenericResponse genericResponse = activityServiceImpl.createActivity(activityCreateRequest);
        assertFalse(genericResponse.getOperationSuccessful());
        assertEquals("Activity cannot be before from the current date!", genericResponse.getMessage());
    }

    @Test
    void testCreateActivity_TooFewParticipants() {
        final String activityTitle = "Activity Title";
        final String activityDescription = "Activity Description";
        final String activityDate = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        final Integer maximumParticipants = 1;
        ActivityCreateRequest activityCreateRequest = new ActivityCreateRequest(activityTitle, activityDescription, activityDate, maximumParticipants);
        GenericResponse genericResponse = activityServiceImpl.createActivity(activityCreateRequest);
        assertFalse(genericResponse.getOperationSuccessful());
        assertEquals("Number of maximum participants must be larger than 2!", genericResponse.getMessage());
    }

    @Test
    void testGetActivity_Success() {
        final Long activityId = 1L;
        final String activityTitle = "Activity Title";
        final String activityDescription = "Activity Description";
        final Date currentDate = new Date();
        final Date futureDate = new Date(currentDate.getTime() + 8640000L);;
        final Integer currentParticipants = 1;
        final Integer maximumParticipants = 5;
        final Boolean isCompleted = false;
        Activity activity = new Activity(activityTitle, activityDescription, currentDate, currentDate, futureDate, currentParticipants, maximumParticipants, isCompleted);
        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));
        ActivityReadResponse response = activityServiceImpl.getActivity(activityId);
        assertTrue(response.getOperationSuccessful());
        assertEquals("Operation successful!", response.getMessage());
        assertEquals(activity, response.getActivity());
    }

    @Test
    void testGetActivity_NotFound() {
        final Long activityId = 1L;
        when(activityRepository.findById(activityId)).thenReturn(Optional.empty());
        ActivityReadResponse response = activityServiceImpl.getActivity(activityId);
        assertFalse(response.getOperationSuccessful());
        assertEquals("Activity not found!", response.getMessage());
    }

    @Test
    void testGetActivityList() {
        final Long activityId = 1L;
        final String activityTitle = "Activity Title";
        final String activityDescription = "Activity Description";
        final Date currentDate = new Date();
        final Date futureDate = new Date(currentDate.getTime() + 8640000L);;
        final Integer currentParticipants = 1;
        final Integer maximumParticipants = 5;
        final Boolean isCompleted = false;
        Activity activity = new Activity(activityTitle, activityDescription, currentDate, currentDate, futureDate, currentParticipants, maximumParticipants, isCompleted);
        when(activityRepository.findAll()).thenReturn(List.of(activity));
        ActivityReadListResponse response = activityServiceImpl.getActivityList();
        assertTrue(response.getOperationSuccessful());
        assertEquals("Operation successful!", response.getMessage());
        assertFalse(response.getActivityList().isEmpty());
        assertEquals(activity, response.getActivityList().getFirst());
    }

    @Test
    void testUpdateActivity_Success() {
        final Long activityId = 1L;
        final String activityTitle = "Activity Title";
        final String activityDescription = "Activity Description";
        final Date currentDate = new Date();
        final Date futureDate = new Date(currentDate.getTime() + 8640000L);;
        final Integer currentParticipants = 1;
        final Integer maximumParticipants = 5;
        final Boolean isCompleted = false;
        final String newActivityTitle = "New Activity Title";
        final String newActivityDescription = "New Activity Description";
        Activity activity = new Activity(activityTitle, activityDescription, currentDate, currentDate, futureDate, currentParticipants, maximumParticipants, isCompleted);
        when(activityRepository.getActivityByActivityId(activityId)).thenReturn(Optional.of(activity));
        ActivityUpdateRequest request = new ActivityUpdateRequest(newActivityTitle, newActivityDescription, null, null, null, null);
        GenericResponse response = activityServiceImpl.updateActivity(activityId, request);
        assertTrue(response.getOperationSuccessful());
        assertEquals("Operation successful!", response.getMessage());
        assertEquals(newActivityTitle, activity.getActivityTitle());
        assertEquals(newActivityDescription, activity.getActivityDescription());
    }

    @Test
    void testUpdateActivity_NotFound() {
        final Long activityId = 1L;
        final String newActivityTitle = "New Activity Title";
        final String newActivityDescription = "New Activity Description";
        when(activityRepository.getActivityByActivityId(activityId)).thenReturn(Optional.empty());
        ActivityUpdateRequest request = new ActivityUpdateRequest(newActivityTitle, newActivityDescription, null, null, null, null);
        GenericResponse response = activityServiceImpl.updateActivity(activityId, request);
        assertFalse(response.getOperationSuccessful());
        assertEquals("Activity not found!", response.getMessage());
    }

    @Test
    void testUpdateActivity_NoUpdatesRequested() {
        final Long activityId = 1L;
        ActivityUpdateRequest request = new ActivityUpdateRequest(null, null, null, null, null, null);
        GenericResponse response = activityServiceImpl.updateActivity(activityId, request);
        assertFalse(response.getOperationSuccessful());
        assertEquals("No update is requested!", response.getMessage());
    }

    @Test
    void testDeleteActivity_Success() {
        final Long activityId = 1L;
        final String activityTitle = "Activity Title";
        final String activityDescription = "Activity Description";
        final Date currentDate = new Date();
        final Date futureDate = new Date(currentDate.getTime() + 8640000L);;
        final Integer currentParticipants = 1;
        final Integer maximumParticipants = 5;
        final Boolean isCompleted = false;
        Activity activity = new Activity(activityTitle, activityDescription, currentDate, currentDate, futureDate, currentParticipants, maximumParticipants, isCompleted);
        when(activityRepository.findById(activityId)).thenReturn(Optional.of(activity));
        GenericResponse response = activityServiceImpl.deleteActivity(activityId);
        assertTrue(response.getOperationSuccessful());
        assertEquals("Operation successful!", response.getMessage());
        verify(activityRepository).delete(activity);
    }

    @Test
    void testDeleteActivity_NotFound() {
        final Long activityId = 1L;
        when(activityRepository.findById(activityId)).thenReturn(Optional.empty());
        GenericResponse response = activityServiceImpl.deleteActivity(activityId);
        assertFalse(response.getOperationSuccessful());
        assertEquals("Activity not found!", response.getMessage());
    }
}
