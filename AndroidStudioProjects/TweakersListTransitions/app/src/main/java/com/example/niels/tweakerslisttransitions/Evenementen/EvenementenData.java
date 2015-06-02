package com.example.niels.tweakerslisttransitions.Evenementen;

import android.content.Context;
import android.widget.Toast;

import com.example.niels.tweakerslisttransitions.ShiftCategorieActivity;

import java.util.ArrayList;
import java.util.HashMap;
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

        cocktailavond.getById("1").getShiften().add(new Shift("20:00", "22:00", "1"));
        cocktailavond.getById("1").getShiften().add(new Shift("22:00", "24:00", "2"));

        fluolicious.getById("1").getShiften().add(new Shift("20:00", "22:00", "1"));
        fluolicious.getById("1").getShiften().add(new Shift("22:00", "24:00", "2"));

        stamavond.getById("1").getShiften().add(new Shift("20:00", "22:00", "1"));
        stamavond.getById("1").getShiften().add(new Shift("22:00", "24:00", "2"));

        try {
            cocktailavond.getById("1").getShiftById("1").voegMedewerkerToe(new Medewerker());
            cocktailavond.getById("1").getShiftById("1").voegMedewerkerToe(new Medewerker());
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

    /**
     * A dummy item representing a piece of content.
     */

}
