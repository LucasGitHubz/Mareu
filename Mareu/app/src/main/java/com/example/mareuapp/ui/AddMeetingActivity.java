package com.example.mareuapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.Model.Meeting;
import com.example.di.DI;
import com.example.mareu.R;
import com.example.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.textInpoutContainer)
    LinearLayout textInputContainer;
    @BindView(R.id.hourLyt)
    TextInputLayout hourInput;
    @BindView(R.id.room_lyt)
    TextInputLayout roomInput;
    @BindView(R.id.topic_lyt)
    TextInputLayout topicInput;
    @BindView(R.id.participants_lyt)
    TextInputLayout participantsInput;
    @BindView(R.id.add_button)
    Button addMeetingBtn;
    @BindView(R.id.chooseColorButton)
    FloatingActionButton chooseColorButton;
    @BindView(R.id.confirmColorBtn)
    Button confirmColorBtn;
    @BindView(R.id.rgbWheel)
    ImageView rgbWheel;

    private MeetingApiService mApiService;
    private Bitmap bitmap;
    private int color = -16777216;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_meeting);
        ButterKnife.bind(this);

        rgbWheel.setDrawingCacheEnabled(true);
        rgbWheel.buildDrawingCache(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        hourInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                addMeetingBtn.setEnabled(s.length() > 0);
            }
        });

        rgbWheel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    bitmap = rgbWheel.getDrawingCache();

                    int pixel = bitmap.getPixel((int)event.getX(), (int)event.getY());

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    color = Color.rgb(r, g ,b);
                    confirmColorBtn.setBackgroundColor(color);
                }
                return true;
            }
        });
    }

    @OnClick(R.id.chooseColorButton)
    void changeColor() {
        textInputContainer.setVisibility(View.INVISIBLE);
        addMeetingBtn.setVisibility(View.INVISIBLE);
        rgbWheel.setVisibility(View.VISIBLE);
        confirmColorBtn.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.confirmColorBtn)
    void saveColor() {
        textInputContainer.setVisibility(View.VISIBLE);
        addMeetingBtn.setVisibility(View.VISIBLE);
        rgbWheel.setVisibility(View.INVISIBLE);
        confirmColorBtn.setVisibility(View.INVISIBLE);
        System.out.println("test" + color);
        chooseColorButton.setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @OnClick(R.id.add_button)
    void addMeeting() {
        Meeting meeting = new Meeting(
                color = color,
                hourInput.getEditText().getText().toString(),
                roomInput.getEditText().getText().toString(),
                topicInput.getEditText().getText().toString(),
                participantsInput.getEditText().getText().toString()
        );
        mApiService.createMeeting(meeting);
        finish();
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
