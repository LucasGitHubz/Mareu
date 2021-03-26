package com.example.Model;

import android.graphics.Color;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Meeting {
    private Integer meetingColor;
    private String startTime;
    private String endTime;
    private String room;
    private String topic;
    private String participants;

    public Meeting(Integer meetingColor, String startTime, String endTime, String room, String topic, String participants) {
        this.meetingColor = meetingColor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.topic = topic;
        this.participants = participants;
    }

    // --- GETTERS ---
    public Integer getColor() {
        return meetingColor;
    }

    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }

    public String getRoom() {
        return room;
    }

    public String getTopic() {
        return topic;
    }

    public String getParticipants() {
        return participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(startTime, meeting.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime);
    }
}