package com.example.niels.tweakerslisttransitions.Evenementen;

/**
 * Created by Niels on 1/06/2015.
 */
public class ShiftCategorie {

    //naam van de categorie, zoals 'toog' of 'inkom'
    private String naam;


    //Wordt gebruikt door arrayadapter voor de tekst van lijst item
    @Override
    public String toString(){

        return naam;

    }


}
