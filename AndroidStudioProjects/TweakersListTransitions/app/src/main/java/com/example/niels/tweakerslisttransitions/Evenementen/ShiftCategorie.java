package com.example.niels.tweakerslisttransitions.Evenementen;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Niels on 1/06/2015.
 */
public class ShiftCategorie implements iShift {

    //naam van de categorie, zoals 'toog' of 'inkom'
    private String naam, id;

    private ArrayList<Shift> shiften;

    public ShiftCategorie(String id, String naam){

        setId(id);
        setNaam(naam);
        shiften = new ArrayList<>();
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Shift> getShiften() {
        return shiften;
    }

    public void setShiften(ArrayList<Shift> shiften) {
        this.shiften = shiften;
    }

    //Wordt gebruikt door arrayadapter voor de tekst van lijst item
    @Override
    public String toString(){

        return naam;

    }

    public Shift getShiftById(String id)throws Resources.NotFoundException{

        for(Shift s : getShiften()){

            if(id.compareTo(s.getId()) == 0)
                return s;

        }
        throw new Resources.NotFoundException("Shift niet gevonden");
    }

    public void addShift(int beginUur, int beginMinuut, int eindUur, int eindMinuut){

        getShiften().add(new Shift(beginUur, beginMinuut, eindUur, eindMinuut, Integer.toString(getShiften().size() + 1)));

    }

    public void addShift(int beginUur, int beginMinuut, int eindUur, int eindMinuut, int medewerkers){

        getShiften().add(new Shift(beginUur, beginMinuut, eindUur, eindMinuut, medewerkers, Integer.toString(getShiften().size() + 1)));


    }


}
