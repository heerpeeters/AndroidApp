package com.example.niels.tweakerslisttransitions.Evenementen;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

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


    public ArrayList<ShiftCategorie> getLijst() {
        return lijst;
    }

    public void setLijst(ArrayList<ShiftCategorie> lijst) {
        this.lijst = lijst;
    }

    private ArrayList<ShiftCategorie> lijst = new ArrayList<>();

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

    //zet date om naar gemakkelijk te lezen string formaat
    private String converteerDatum(Date date){

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        return day + "/" + month + "/" + year;

    }

    //Staat toe om een shiftcategorie op id te vinden

    public ShiftCategorie getById(String id)throws Resources.NotFoundException{

        for(ShiftCategorie sc : getLijst()){

            if(id.compareTo(sc.getId()) == 0)
                return sc;

        }
        throw new Resources.NotFoundException("Shiftcategorie niet gevonden");
    }

    public void voegShiftCategorieToe(ShiftCategorie sc) {

        getLijst().add(sc);

    }
}
