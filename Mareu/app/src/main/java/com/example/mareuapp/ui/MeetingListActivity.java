package com.example.mareuapp.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Model.Meeting;
import com.example.di.DI;
import com.example.mareu.R;
import com.example.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MeetingListActivity extends AppCompatActivity implements MeetingListAdapter.Listener {
    // FOR DESIGN ---
    RecyclerView recyclerView;
    FloatingActionButton fab;

    // FOR DATA ---
    private MeetingListAdapter adapter;
    private MeetingApiService mApiService;

    // OVERRIDE ---

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_meeting);
        mApiService = DI.getMeetingApiService();
        configureFab();
        configureRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    // CONFIGURATION ---

    private void configureRecyclerView() {
        recyclerView = findViewById(R.id.activity_list_user_rv);
        adapter = new MeetingListAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void configureFab() {
        fab = findViewById(R.id.add_meeting_button);
    }

    private void loadData() {
        adapter.updateList(mApiService.getMeetings());
    }

    // ACTIONS ---

    @Override
    public void onClickDelete(Meeting meeting) {
        mApiService.deleteMeeting(meeting);
        loadData();
    }
}
