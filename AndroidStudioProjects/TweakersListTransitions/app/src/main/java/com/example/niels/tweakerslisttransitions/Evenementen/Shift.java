package com.example.niels.tweakerslisttransitions.Evenementen;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Niels on 1/06/2015.
 */
public class Shift implements iShift{

    private String id;

    //later vervangen door Date of Time, zodat we met timepicker kunnen werken
    private int beginMinuut, beginUur, eindMinuut, eindUur;

    private LocalTime startTime, endTime;

    //lijst met medewerkers
    private List<Medewerker> medewerkers;

    //Het aantal medewerkers nodig voor de shift
    private int aantalMedewerkersNodig;

    public Shift(LocalTime startTime, LocalTime endTime, int aantalMederwerkers, String id)
    {

        setStartTime(startTime);
        setEndTime(endTime);
        setAantalMedewerkersNodig(aantalMederwerkers);
        setId(id);
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
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
    public String toString()
    {

        return startTime.toString("HH:mm") + " - " + endTime.toString("HH:mm");

    }

    public String toStringWorkers()
    {

        return getMedewerkers().size() + "/" + getAantalMedewerkersNodig();

    }
}
