package com.example.Model;

import android.graphics.Color;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Meeting {
    private Integer meetingColor;
    private String hour;
    private String room;
    private String topic;
    private String participants;

    public Meeting(Integer meetingColor, String hour, String room, String topic, String participants) {
        this.meetingColor = meetingColor;
        this.hour = hour;
        this.room = room;
        this.topic = topic;
        this.participants = participants;
    }

    // --- GETTERS ---
    public Integer getColor() {
        return meetingColor;
    }

    public String getMeetingInformation() {
        return room + " - " + hour + " - " + topic;
    }

    public String getParticipants() {
        return participants;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Meeting)) return false;
        return (((Meeting) obj).room == this.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(room);
    }
}