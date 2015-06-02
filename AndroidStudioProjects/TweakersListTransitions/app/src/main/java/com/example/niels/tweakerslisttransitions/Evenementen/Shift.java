package com.example.niels.tweakerslisttransitions.Evenementen;

import java.util.List;

/**
 * Created by Niels on 1/06/2015.
 */
public class Shift {



    //later vervangen door Date of Time, zodat we met timepicker kunnen werken
    private String beginUur, eindUur;

    //lijst met medewerkers
    private List<Medewerker> medewerkers;

    //Het aantal medewerkers nodig voor de shift
    private int aantalMedewerkersNodig;

    public Shift(String start, String einde){

        setBeginUur(start);
        setEindUur(einde);

    }

    public String getBeginUur() {
        return beginUur;
    }

    public void setBeginUur(String beginUur) {
        this.beginUur = beginUur;
    }
    public String getEindUur() {
        return eindUur;
    }

    public void setEindUur(String eindUur) {
        this.eindUur = eindUur;
    }

    public List<Medewerker> getMedewerkers() {
        return medewerkers;
    }

    public void setMedewerkers(List<Medewerker> medewerkers) {
        this.medewerkers = medewerkers;
    }

    public int getAantalMedewerkersNodig() {
        return aantalMedewerkersNodig;
    }

    public void setAantalMedewerkersNodig(int aantalMedewerkersNodig) {
        this.aantalMedewerkersNodig = aantalMedewerkersNodig;
    }

    //indien het aantal medewerkers nog niet bereikt is, voeg medewerker toe. Anders gooi exception
    public void voegMedewerkerToe(Medewerker medewerker) throws Exception {

        if(getMedewerkers().size() < getAantalMedewerkersNodig())
            getMedewerkers().add(medewerker);
        else
            throw new Exception("Maximum aantal medewerkers bereikt");

    }

    //Wordt gebruikt door arrayadpter voor de tekst voor lijst item
    @Override
    public String toString(){

        return getBeginUur() + getEindUur() + getMedewerkers().size() + "/" + getAantalMedewerkersNodig();

    }
}
