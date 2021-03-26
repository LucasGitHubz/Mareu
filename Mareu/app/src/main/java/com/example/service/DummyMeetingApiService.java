package com.example.service;

import com.example.Model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {
    private List<Meeting> meetings = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() { return meetings; }

    @Override
    public void createMeeting(Meeting meeting) { meetings.add(meeting); }

    @Override
    public void clearMeetings() {
        meetings.clear();
    }

    @Override
    public void deleteMeeting(Meeting meeting) { meetings.remove(meeting); }
}
