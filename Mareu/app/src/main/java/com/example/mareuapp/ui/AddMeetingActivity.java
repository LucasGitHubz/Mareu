package com.example.mareuapp.ui;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.Model.Meeting;
import com.example.di.DI;
import com.example.mareu.R;
import com.example.service.MeetingApiService;
import com.example.utils.CalendarParser;
import com.example.utils.CustomValidator;
import com.example.utils.MyObserver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class AddMeetingActivity extends AppCompatActivity {
    @BindView(R.id.startTime_button)
    Button startTimeBtn;
    @BindView(R.id.endTime_button)
    Button endTimeBtn;
    @BindView(R.id.textInpoutContainer)
    LinearLayout textInputContainer;
    @BindView(R.id.room_lyt)
    EditText roomInput;
    @BindView(R.id.topic_lyt)
    EditText topicInput;
    @BindView(R.id.participants_lyt)
    EditText participantsInput;
    @BindView(R.id.addingErrorTV)
    TextView errorMessage;
    @BindView(R.id.add_button)
    Button addMeetingBtn;

    private MeetingApiService mApiService;
    private Bitmap bitmap;
    private int color = -16777216;
    TimePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_meeting);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getMeetingApiService();
        hourInputInit();

        Map map = new HashMap<Integer, CustomValidator>();
        map.put(R.id.room_lyt, new CustomValidator() {
            @Override
            public boolean validate(EditText editText) {
                return isTimeAndRoomAvailable();
            }
        });

        map.put(R.id.topic_lyt, new CustomValidator() {
            @Override
            public boolean validate(EditText editText) {
                return editText.length() > 0;
            }
        });
        map.put(R.id.participants_lyt, new CustomValidator() {
            @Override
            public boolean validate(EditText editText) {
                return editText.length() > 0;
            }
        });
        MyObserver observer = new MyObserver(addMeetingBtn, textInputContainer, map);
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

    private void hourInputInit() {
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
    }

    private Boolean isTimeAndRoomAvailable() {
        if (roomInput.getText().length() > 0) {
            List<Meeting> meetings = mApiService.getMeetings();
            for (Meeting meeting : meetings) {
                Calendar calendar1 = CalendarParser.getCalendar(meeting.getStartTime());
                Calendar calendar2 = CalendarParser.getCalendar(meeting.getEndTime());
                Calendar calendar3 = CalendarParser.getCalendar(startTimeBtn.getText().toString());
                Calendar calendar4 = CalendarParser.getCalendar(endTimeBtn.getText().toString());

                if (roomInput.getText().toString().equals(meeting.getRoom())) {
                    if (calendar3.getTime().before(calendar1.getTime()) || (calendar3.getTime().equals(calendar1.getTime()))) {
                        if (calendar4.getTime().after(calendar1.getTime())) {
                                errorMessage.setText("Cette salle de réunion est déjà utilisée pour ce créneau horaire");
                                return false;
                        }
                    }
                    if (calendar3.getTime().before(calendar2.getTime()) && calendar3.getTime().after(calendar1.getTime()) ||
                            (calendar3.getTime().equals(calendar1.getTime()) && calendar3.getTime().before(calendar2.getTime())))  {
                        errorMessage.setText("Cette salle de réunion est déjà utilisée pour ce créneau horaire");
                        return false;
                    }
                }
            }
        }
        errorMessage.setText("");
        return true;
    }

    private void hourCheck(Button button) {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(AddMeetingActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        button.setText(sHour + ":" + sMinute);
                        if (startTimeBtn.getText().toString().equals(endTimeBtn.getText().toString())) {
                            errorMessage.setText("Veuillez choisir deux horaires différents");
                        } else {
                            if (isTimeAndRoomAvailable() &&
                                    topicInput.getText().length() > 0 &&
                                    participantsInput.getText().length() > 0) {
                                addMeetingBtn.setEnabled(true);
                            } else {
                                addMeetingBtn.setEnabled(false);
                            }
                        }
                    }
                }, hour, minutes, true);
        picker.show();
    }

    @OnClick(R.id.add_button)
    void addMeeting() {
        Meeting meeting = new Meeting(
                color = color,
                startTimeBtn.getText().toString(),
                endTimeBtn.getText().toString(),
                roomInput.getText().toString(),
                topicInput.getText().toString(),
                participantsInput.getText().toString()
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

