package com.example.niels.tweakerslisttransitions.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.example.niels.tweakerslisttransitions.Evenementen.ShiftCategorie;
import com.example.niels.tweakerslisttransitions.ShiftCategorieActivity;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private String title;

    private boolean displaySecondaryFragment = true;

    private int beginuur, beginminuut, einduur, eindminuut;

    private ShiftCategorie shiftCategorie;

    private ShiftCategorieActivity shiftCategorieActivity;

    int medewerkers;

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

        if(displaySecondaryFragment)
        {

            TimePickerFragment timePickerEinduur = new TimePickerFragment();
            timePickerEinduur.setTitle("Einduur");
            timePickerEinduur.setDisplaySecondaryFragment(false);
            timePickerEinduur.setBeginuur(hourOfDay);
            timePickerEinduur.setBeginminuut(minute);
            timePickerEinduur.setShiftCategorie(shiftCategorie);
            timePickerEinduur.setShiftCategorieActivity(shiftCategorieActivity);
            timePickerEinduur.show(this.getFragmentManager(), "Einduur");

        }
        else
        {

            einduur = hourOfDay;
            eindminuut = minute;

            AlertDialog.Builder alert = new AlertDialog.Builder(shiftCategorieActivity);

            alert.setTitle("Aantal medewerkers");
            alert.setMessage("Geef het maximum aantal medewerkers");

            // Set an EditText view to get user input
            final NumberPicker input = new NumberPicker(shiftCategorieActivity);

            String[] nums = new String[100];
            for (int i=0; i<nums.length; i++)
                nums[i] = Integer.toString(i);

            input.setMinValue(1);
            input.setMaxValue(nums.length - 1);
            input.setWrapSelectorWheel(false);
            input.setDisplayedValues(nums);
            input.setValue(2);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    medewerkers = input.getValue() - 1;
                    //add the shift to the shiftcategorie
                    getShiftCategorie().addShift(beginuur, beginminuut, einduur, eindminuut, medewerkers);
                    shiftCategorieActivity.reloadData();
                }
            });

            alert.setView(input);

            alert.show();

        }

        
    }

    public void setTitle(String title)
    {

        this.title = title;

    }

    public int getBeginuur() {
        return beginuur;
    }

    public void setBeginuur(int beginuur) {
        this.beginuur = beginuur;
    }

    public int getBeginminuut() {
        return beginminuut;
    }

    public void setBeginminuut(int beginminuut) {
        this.beginminuut = beginminuut;
    }

    public int getEinduur() {
        return einduur;
    }

    public void setEinduur(int einduur) {
        this.einduur = einduur;
    }

    public int getEindminuut() {
        return eindminuut;
    }

    public void setEindminuut(int eindminuut) {
        this.eindminuut = eindminuut;
    }

    public void setDisplaySecondaryFragment(boolean displaySecondaryFragment) {
        this.displaySecondaryFragment = displaySecondaryFragment;
    }

    public ShiftCategorie getShiftCategorie() {
        return shiftCategorie;
    }

    public void setShiftCategorie(ShiftCategorie shiftCategorie) {
        this.shiftCategorie = shiftCategorie;
    }

    public ShiftCategorieActivity getShiftCategorieActivity() {
        return shiftCategorieActivity;
    }

    public void setShiftCategorieActivity(ShiftCategorieActivity shiftCategorieActivity) {
        this.shiftCategorieActivity = shiftCategorieActivity;
    }
}