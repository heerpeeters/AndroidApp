package com.example.niels.tweakerslisttransitions.Evenementen;

import android.content.Context;
import android.widget.Toast;

import com.example.niels.tweakerslisttransitions.ShiftCategorieActivity;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class EvenementenData {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Evenement> ITEMS = new ArrayList<Evenement>();
    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Evenement> ITEM_MAP = new HashMap<String, Evenement>();

    static {
        // Add 3 sample items.
        Evenement cocktailavond = new Evenement("1", "Cocktailavond");
        Evenement fluolicious = new Evenement("2", "Fluolicious");
        Evenement stamavond = new Evenement("3", "Gewone Stamavond");

        cocktailavond.getLijst().add(new ShiftCategorie("1", "Toog"));
        cocktailavond.getLijst().add(new ShiftCategorie("2", "Inkom"));

        fluolicious.getLijst().add(new ShiftCategorie("1", "Toog"));
        stamavond.getLijst().add(new ShiftCategorie("1", "Toog"));

        //cocktailavond.getById("1").getShiften().add(new Shift(20, 0, 22, 0, "1"));
        cocktailavond.getById("1").getShiften().add(new Shift(new LocalTime(20, 0), new LocalTime(22, 0), 3, "1"));
        cocktailavond.getById("1").getShiften().add(new Shift(new LocalTime(22, 0), new LocalTime(0, 0), 3, "2"));

        fluolicious.getById("1").getShiften().add(new Shift(new LocalTime(20, 0), new LocalTime(22, 0), 3, "1"));
        fluolicious.getById("1").getShiften().add(new Shift(new LocalTime(22, 0), new LocalTime(0, 0), 3, "2"));

        stamavond.getById("1").getShiften().add(new Shift(new LocalTime(20, 0), new LocalTime(22, 0), 3, "1"));
        stamavond.getById("1").getShiften().add(new Shift(new LocalTime(22, 0), new LocalTime(0, 0), 3, "2"));

        try {
            cocktailavond.getById("1").getShiftById("1").voegMedewerkerToe(new Medewerker("Rudy"));
            cocktailavond.getById("1").getShiftById("1").voegMedewerkerToe(new Medewerker("Freddy"));
            //cocktailavond.getById("1").getShiftById("2").voegMedewerkerToe(new Medewerker("Tom"));
        } catch (Exception e) {

        }

        addItem(cocktailavond);
        addItem(fluolicious);
        addItem(stamavond);
    }

    private static void addItem(Evenement evenement) {
        ITEMS.add(evenement);
        ITEM_MAP.put(evenement.getId(), evenement);
    }

    public static Shift getShiftById(String eventId, String id, String categorieId){

        Evenement event = ITEM_MAP.get(eventId);

        for(ShiftCategorie sc : event.getLijst()){

            if(sc.getId().equals(categorieId))
                return sc.getShiftById(id);

        }
        return null;

        /*
        for(Map.Entry<String, Evenement> entry : ITEM_MAP.entrySet()){

            for(ShiftCategorie sc : entry.getValue().getLijst()){

               for(Shift s : sc.getShiften()){

                   if(id.compareTo(s.getId()) == 0)
                       return s;

               }

            }

        }
        return null;*/
    }

    public static void orderEventsByDate(){

        //converting map to linkedlist
        List<Evenement> list = new LinkedList(ITEM_MAP.values());
        //ordering events by date
        Collections.sort(list, new Comparator<Evenement>(){

            public int compare(Evenement e1, Evenement e2){

                return e2.getDatum().compareTo(e1.getDatum());

            }

        });

        Map<String, Evenement> newMap = new HashMap<>();

        for(Evenement e: list){

            newMap.put(e.getId(), e);

        }

        ITEM_MAP = newMap;

    }

    public static void deleteEvent(int id)
    {

        ITEM_MAP.remove(ITEM_MAP.get(id));

    }

}
