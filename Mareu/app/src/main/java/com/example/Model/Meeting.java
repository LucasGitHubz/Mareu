package com.example.Model;

import android.graphics.Color;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Meeting {
    private Color meetingColor;
    private String meetingInformation;
    private String participants;

    public Meeting(Color meetingColor, String meetingInformation, String participants) {
        this.meetingColor = meetingColor;
        this.meetingInformation = meetingInformation;
        this.participants = participants;
    }

    // --- GETTERS ---
    public Color getColor() {
        return meetingColor;
    }

    public String getMeetingInformation() {
        return meetingInformation;
    }

    public String getParticipants() {
        return participants;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Meeting)) return false;
        return (((Meeting) obj).meetingInformation == this.meetingInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetingInformation);
    }
}