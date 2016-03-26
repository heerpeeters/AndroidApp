package com.example.niels.tweakerslisttransitions.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private String title;

    private boolean displaySecondaryFragment;

    private int uur, minuut;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog tmp = new TimePickerDialog(getActivity(),this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        tmp.setTitle(title);

        return tmp;

        // Create a new instance of TimePickerDialog and return it
        //return new TimePickerDialog(getActivity(), this, hour, minute,
          //      DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if(!displaySecondaryFragment)
        {

            uur = hourOfDay;
            minuut = minute;

            displaySecondaryFragment = true;

            TimePickerFragment timePickerEinduur = new TimePickerFragment();
            timePickerEinduur.setTitle("Einduur");
            timePickerEinduur.show(this.getFragmentManager(), "Einduur");

        }

    }

    public void setTitle(String title)
    {

        this.title = title;

    }

    public int getUur() {
        return uur;
    }

    public int getMinuut() {
        return minuut;
    }

}