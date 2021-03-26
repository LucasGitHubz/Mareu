package com.example.service;

import com.example.Model.Meeting;

import java.util.List;

public interface MeetingApiService {
    List<Meeting> getMeetings();

    /**
     * Deletes a meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    void clearMeetings();
}
