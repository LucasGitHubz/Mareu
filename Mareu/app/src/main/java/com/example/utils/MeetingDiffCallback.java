package com.example.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.example.Model.Meeting;

import java.util.List;

public class MeetingDiffCallback extends DiffUtil.Callback{

    private final List<Meeting> oldMeetings;
    private final List<Meeting> newMeeting;

    public MeetingDiffCallback(List<Meeting> newMeeting, List<Meeting> oldMeetings) {
        this.newMeeting = newMeeting;
        this.oldMeetings = oldMeetings;
    }

    @Override
    public int getOldListSize() {
        return oldMeetings.size();
    }

    @Override
    public int getNewListSize() {
        return newMeeting.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMeetings.get(oldItemPosition).getStartTime() == newMeeting.get(newItemPosition).getStartTime() ;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMeetings.get(oldItemPosition).equals(newMeeting.get(newItemPosition));
    }
}
