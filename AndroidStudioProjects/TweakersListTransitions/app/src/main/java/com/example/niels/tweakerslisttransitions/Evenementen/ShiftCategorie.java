package com.example.niels.tweakerslisttransitions.Evenementen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Niels on 1/06/2015.
 */
public class ShiftCategorie {

    //naam van de categorie, zoals 'toog' of 'inkom'
    private String naam, id;

    private ArrayList<Shift> shiften = new ArrayList<>();

    public ShiftCategorie(String id, String naam){

        setId(id);
        setNaam(naam);

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


}
