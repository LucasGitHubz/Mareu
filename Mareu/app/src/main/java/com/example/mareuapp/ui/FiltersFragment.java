package com.example.mareuapp.ui;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import android.app.DialogFragment;

import com.example.Model.Meeting;
import com.example.di.DI;
import com.example.mareu.R;
import com.example.service.MeetingApiService;
import com.example.utils.CalendarParser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FiltersFragment extends DialogFragment {
    Button startTimeBtn;
    Button endTimeBtn;
    EditText roomEditText;
    TextView noMeetingTV;
    TextView noRoomTV;
    Button validFilterButton;
    TimePickerDialog picker;

    private MeetingApiService mApiService;
    private MeetingListActivity mListActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filter_layout, container, false);

        ContextWrapper context = (ContextWrapper) v.getContext();
        mListActivity = (MeetingListActivity) context.getBaseContext();
        configureFilters(v);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    private void configureFilters(View v) {
        startTimeBtn = v.findViewById(R.id.startTimeButton);
        endTimeBtn = v.findViewById(R.id.endTimeButton);
        roomEditText = v.findViewById(R.id.roomEditText);
        noMeetingTV = v.findViewById(R.id.noMeetingText);
        noRoomTV = v.findViewById(R.id.noRoomText);
        validFilterButton = v.findViewById(R.id.validFiltersBtn);

        startTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourCheck(startTimeBtn);
            }
        });

        endTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourCheck(endTimeBtn);
            }
        });
        roomEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (roomEditText.getText().length() > 0) {
                    if (isValidRoomName()) {
                        noRoomTV.setVisibility(View.INVISIBLE);
                        mListActivity.loadData(filteredListByName(roomEditText.getText().toString()));
                    } else {
                        noRoomTV.setVisibility(View.VISIBLE);
                    }
                } else {
                    noRoomTV.setVisibility(View.INVISIBLE);
                }
            }
        });

        validFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void hourCheck(Button button) {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(button.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        button.setText(sHour + ":" + sMinute);
                        if (startTimeBtn.getText().toString().equals(endTimeBtn.getText().toString())) {
                            noMeetingTV.setText("Veuillez choisir deux horaires différents");
                            noMeetingTV.setVisibility(View.VISIBLE);
                        } else {
                            List<Meeting> filteredMeetings = filteredListByHour(startTimeBtn.getText().toString(), endTimeBtn.getText().toString());
                            if (filteredMeetings.size() < 1) {
                                noMeetingTV.setText("Aucune réunion créée avec ces horaires");
                                noMeetingTV.setVisibility(View.VISIBLE);
                            } else {
                                noMeetingTV.setVisibility(View.INVISIBLE);
                                mListActivity.loadData(filteredMeetings);
                            }
                        }
                    }
                }, hour, minutes, true);
        picker.show();
    }

    private List<Meeting> filteredListByHour(String startTime, String endTime) {
        List<Meeting> filteredMeetings = new ArrayList<>();
        for (Meeting meeting: mApiService.getMeetings()) {
            Calendar calendar1 = CalendarParser.getCalendar(meeting.getStartTime());
            Calendar calendar2 = CalendarParser.getCalendar(meeting.getEndTime());
            Calendar calendar3 = CalendarParser.getCalendar(startTime);
            Calendar calendar4 = CalendarParser.getCalendar(endTime);

            if (calendar3.getTime().before(calendar1.getTime()) || (calendar3.getTime().equals(calendar1.getTime()))) {
                if (calendar4.getTime().after(calendar2.getTime()) || (calendar4.getTime().equals(calendar2.getTime()))) {
                    filteredMeetings.add(meeting);
                }
            }
        }
        return filteredMeetings;
    }

    private List<Meeting> filteredListByName(String roomName) {
        List<Meeting> filteredMeetings = new ArrayList<>();
        for (Meeting meeting: mApiService.getMeetings()) {
            if (meeting.getRoom().equals(roomName)) {
                filteredMeetings.add(meeting);
            }
        }
        return filteredMeetings;
    }

    private Boolean isValidRoomName() {
        for (Meeting meeting: mApiService.getMeetings()) {
            if (meeting.getRoom().equals(roomEditText.getText().toString())) {
                return true;
            }
        }
        return false;
    }
}
