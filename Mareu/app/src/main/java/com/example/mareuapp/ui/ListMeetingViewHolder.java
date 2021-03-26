package com.example.mareuapp.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;

import com.example.Model.Meeting;

public class ListMeetingViewHolder extends RecyclerView.ViewHolder {
    // FOR DESIGN ---
    private View colorView;
    private TextView meetingInformation;
    private TextView participants;
    private ImageButton deleteButton;

    public ListMeetingViewHolder(@NonNull View itemView) {
        super(itemView);

        colorView = itemView.findViewById(R.id.item_list_meeting_color);
        meetingInformation = itemView.findViewById(R.id.item_list_meeting_information);
        participants = itemView.findViewById(R.id.item_list_meeting_participants);
        deleteButton = itemView.findViewById(R.id.item_list_meeting_delete_button);
    }

    public void bind(Meeting meeting, MeetingListAdapter.Listener callback) {
        colorView.setBackgroundTintList(ColorStateList.valueOf(meeting.getColor()));
        meetingInformation.setText(meeting.getRoom() + " - " + meeting.getStartTime() + "-" + meeting.getEndTime() + " - " + meeting.getTopic());
        participants.setText(meeting.getParticipants());
        deleteButton.setOnClickListener(view -> callback.onClickDelete(meeting));
    }
}
