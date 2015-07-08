package com.example.niels.tweakerslisttransitions;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.niels.tweakerslisttransitions.Evenementen.EvenementenData;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Niels on 19/06/2015.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    //this is to communicate id of newly created event
    private int id;

    private Calendar kalender;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        kalender = Calendar.getInstance();
        kalender.set(year, monthOfYear, dayOfMonth);
        EvenementenData.ITEM_MAP.get(Integer.toString(id)).setDatum(kalender.getTime());
        EvenementenData.orderEventsByDate();
        //use this to reload the date right after event has been created.
        ((EvenementListActivity)getActivity()).reloadData();
        dismiss();
    }

    public void setId(int id) {
        this.id = id;
    }

}
