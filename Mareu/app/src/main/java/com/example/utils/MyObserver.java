package com.example.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MyObserver {
    public MyObserver(Button addMeetingButton, ViewGroup layout, Map<Integer, CustomValidator> validators) {
        this.addMeetingButton = addMeetingButton;
        this.layout = layout;
        this.validators = validators;
        getAllEditTextLayout();
    }

    private Button addMeetingButton;
    private Map<EditText, Boolean> validationMap = new HashMap<>();
    private boolean isValidated = false;
    private ViewGroup layout;
    private Map<Integer, CustomValidator> validators;

    public void getAllEditTextLayout() {
        for(int i = 0; i < layout.getChildCount(); i++){
            View v = layout.getChildAt(i);
            if(v instanceof EditText) {
                EditText editText = (EditText) v;

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {}

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        update(editText, validators.get(v.getId()).validate(editText));
                    }
                });
                validationMap.put(editText, false);
            }
        }
    }

    public void update(EditText key, Boolean isFullField) {
        validationMap.put(key, isFullField);
        if (isFullField) {
            isValidated = true;
            for (Boolean b : validationMap.values()) {
                if (b != true) {
                    isValidated = false;
                    break;
                }
            }
            addMeetingButton.setEnabled(isValidated);
        } else {
            addMeetingButton.setEnabled(false);
        }
    }
}
