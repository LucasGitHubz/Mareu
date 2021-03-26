package com.example.mareuapp.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Meeting;
import com.example.di.DI;
import com.example.mareu.R;
import com.example.service.MeetingApiService;
import com.example.utils.CalendarParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import butterknife.OnClick;

public class MeetingListActivity extends AppCompatActivity implements MeetingListAdapter.Listener {
    // FOR DESIGN ---
    RecyclerView recyclerView;
    FloatingActionButton fab;
    TextView noMeetingText;
    Button filterButton;
    Button cancelFilterButton;

    // FOR DATA ---
    MeetingListAdapter adapter;
    private MeetingApiService mApiService;

    // OVERRIDE ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_meeting);
        mApiService = DI.getMeetingApiService();
        filterButton = findViewById(R.id.filterBtn);
        cancelFilterButton = findViewById(R.id.cancelFilterBtn);
        configureFab();
        configureRecyclerView();
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }

                ft.addToBackStack(null);
                DialogFragment dialogFragment = new FiltersFragment();
                dialogFragment.show(ft, "dialog");
            }
        });

        cancelFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(mApiService.getMeetings());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(mApiService.getMeetings());
    }

    // CONFIGURATION ---

    private void configureRecyclerView() {
        noMeetingText = findViewById(R.id.noMeetingTV);
        recyclerView = findViewById(R.id.activity_list_user_rv);
        adapter = new MeetingListAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void configureFab() {
        fab = findViewById(R.id.add_meeting_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMeetingActivity.navigate(MeetingListActivity.this);
            }
        });
    }

    void loadData(List<Meeting> meetingList) {
        adapter.updateList(meetingList);
        if (mApiService.getMeetings().size() < 1) {
            recyclerView.setVisibility(View.INVISIBLE);
            noMeetingText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noMeetingText.setVisibility(View.INVISIBLE);
        }
    }

    // ACTIONS ---

    @Override
    public void onClickDelete(Meeting meeting) {
        mApiService.deleteMeeting(meeting);
        loadData(mApiService.getMeetings());
    }
}
