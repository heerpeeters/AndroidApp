package com.example.niels.tweakerslisttransitions.Evenementen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Niels on 1/06/2015.
 */
public class Evenement {

    public Evenement(String id, String naam){

        setId(id);
        setNaam(naam);
        Calendar c = new GregorianCalendar();

        setDatum(c.getTime());

    }


    public ArrayList<Shift> getLijst() {
        return lijst;
    }

    public void setLijst(ArrayList<Shift> lijst) {
        this.lijst = lijst;
    }

    private ArrayList<Shift> lijst = new ArrayList<>();

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    private String naam;

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    private Date datum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    //Gebruikt door ArrayAdapter om de tekst voor lijst item weer te geven
    @Override
    public String toString(){

        return getNaam() + " " + converteerDatum(getDatum());

    }

    private String converteerDatum(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        return day + "/" + month + "/" + year;

    }
}
