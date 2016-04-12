package com.example.niels.tweakerslisttransitions.Evenementen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 1/06/2015.
 */
public class Shift implements iShift{

    private String id;

    //later vervangen door Date of Time, zodat we met timepicker kunnen werken
    private int beginMinuut, beginUur, eindMinuut, eindUur;

    //lijst met medewerkers
    private List<Medewerker> medewerkers;

    //Het aantal medewerkers nodig voor de shift
    private int aantalMedewerkersNodig = 3;

    public Shift(int beginUur, int beginMinuut, int eindUur, int eindMinuut, String id){

        setId(id);
        setBeginUur(beginUur);
        setBeginMinuut(beginMinuut);
        setEindUur(eindUur);
        setEindMinuut(eindMinuut);
        medewerkers = new ArrayList<>();
    }

    public Shift(int beginUur, int beginMinuut, int eindUur, int eindMinuut, int aantalMedewerkers, String id){

        setId(id);
        setBeginUur(beginUur);
        setBeginMinuut(beginMinuut);
        setEindUur(eindUur);
        setEindMinuut(eindMinuut);
        setAantalMedewerkersNodig(aantalMedewerkers);
        medewerkers = new ArrayList<>();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //indien het aantal medewerkers nog niet bereikt is, voeg medewerker toe. Anders gooi exception
    public void voegMedewerkerToe(Medewerker medewerker) throws Exception {

        if(!(isMaximumAantalMedewerkersBereikt()))
            getMedewerkers().add(medewerker);
        else
            throw new Exception("Maximum aantal medewerkers bereikt");

    }

    public int getBeginMinuut() {
        return beginMinuut;
    }

    public void setBeginMinuut(int beginMinuut) {
        this.beginMinuut = beginMinuut;
    }

    public int getBeginUur() {
        return beginUur;
    }

    public void setBeginUur(int beginUur) {
        this.beginUur = beginUur;
    }

    public int getEindMinuut() {
        return eindMinuut;
    }

    public void setEindMinuut(int eindMinuut) {
        this.eindMinuut = eindMinuut;
    }

    public int getEindUur() {
        return eindUur;
    }

    public void setEindUur(int eindUur) {
        this.eindUur = eindUur;
    }

    public boolean isMaximumAantalMedewerkersBereikt(){

        return getMedewerkers().size() >= getAantalMedewerkersNodig();

    }

    //methode om medewerker te verwijderen uit lijst met medewerkers
    public void verwijderMedewerker(int positie){

        getMedewerkers().remove(positie);

    }

    //Wordt gebruikt door arrayadpter voor de tekst voor lijst item
    @Override
    public String toString(){

        String beginUur, beginMinuut, eindUur, eindMinuut;

        if(getBeginUur() < 10)
            beginUur = "0" + getBeginUur();
        else
            beginUur = Integer.toString(getBeginUur());

        if(getBeginMinuut() < 10)
            beginMinuut = "0" + getBeginMinuut();
        else
            beginMinuut = Integer.toString(getBeginMinuut());

        if(getEindUur() < 10)
            eindUur = "0" + getEindUur();
        else
            eindUur = Integer.toString(getEindUur());

        if(getEindMinuut() < 10)
            eindMinuut = "0" + getEindMinuut();
        else
            eindMinuut = Integer.toString(getEindMinuut());

        return beginUur + ":" + beginMinuut + " - " + eindUur + ":" + eindMinuut + "              " + getMedewerkers().size() + "/" + getAantalMedewerkersNodig();

    }
}
