package com.example.niels.tweakerslisttransitions.Evenementen;

/**
 * Created by Niels on 1/06/2015.
 */
public class Medewerker {

    private String naam;

    private int id;

    public Medewerker(String naam){

        setNaam(naam);

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString(){

        return naam;

    }
}
