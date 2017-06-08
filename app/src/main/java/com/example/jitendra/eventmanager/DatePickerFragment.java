package com.example.jitendra.eventmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.DatePicker;

/**
 * Created by jitendra on 3/6/17.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    static final int START_DATE = 1;
    static final int END_DATE = 2;

    private int mChosenDate;

    int cur = 0;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mChosenDate = bundle.getInt("DATE", 1);
        }


        switch (mChosenDate) {

            case START_DATE:
                cur = START_DATE;
                return new DatePickerDialog(getActivity(), this, year, month, day);

            case END_DATE:
                cur = END_DATE;
                return new DatePickerDialog(getActivity(), this, year, month, day);

        }
        return null;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        if (cur == START_DATE) {
            // set selected date into textview
            Log.v("Date Début", "Date1 : " + new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        } else {
            Log.v("Date fin", "Date2 : " + new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    }
}