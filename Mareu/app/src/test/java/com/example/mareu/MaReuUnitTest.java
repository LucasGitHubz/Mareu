package com.example.mareu;

import com.example.Model.Meeting;
import com.example.di.DI;
import com.example.service.MeetingApiService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class MaReuUnitTest {
    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getMeetingApiService();
        service.clearMeetings();
    }

    @Test
    public void getMeetingsWithSuccess() {
        Meeting meeting1 = new Meeting(1, "10:00","14h00", "Alpha", "Peach", "user12345§@mareu.com");
        Meeting meeting2 = new Meeting(1, "10:00","14h00", "Alpha", "Peach", "user12345§@mareu.com");

        service.createMeeting(meeting1);
        service.createMeeting(meeting2);

        Integer meetings = service.getMeetings().size();

        assertTrue(meetings == 2);
    }

    @Test
    public void addMeetingWithSuccess() {
        Integer meetingsBeforeAdding = service.getMeetings().size();
        Meeting meeting = new Meeting(1, "10:00","14h00", "Alpha", "Peach", "user12345§@mareu.com");
        service.createMeeting(meeting);
        Integer meetingAfterAdding = service.getMeetings().size();
        assertTrue(meetingsBeforeAdding < meetingAfterAdding);
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = new Meeting(1, "10:00","14h00", "Alpha", "Peach", "user12345§@mareu.com");

        service.createMeeting(meetingToDelete);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }
}