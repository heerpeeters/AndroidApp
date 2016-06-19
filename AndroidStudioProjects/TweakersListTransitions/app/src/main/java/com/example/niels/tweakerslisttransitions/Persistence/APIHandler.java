package com.example.niels.tweakerslisttransitions.Persistence;

import android.os.AsyncTask;
import android.util.Log;

import com.example.niels.tweakerslisttransitions.Evenementen.Evenement;

import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by niels on 15/06/2016.
 */
public class APIHandler extends AsyncTask {

    final private String apiUrl = "http://shiftmanagerapi.azurewebsites.net/api/shift";

    private String test = null;

    private JSONArray dataJsonArr = null;

    private ArrayList<Evenement> events;

    public void doQuery()
    {

        this.execute();

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            JsonParser jParser = new JsonParser();

            // get json string from url
            //JSONObject json = jParser.getJSONFromUrl(apiUrl);

            dataJsonArr = jParser.getJSONFromUrl(apiUrl);//json.getJSONArray("ArrayOfEvent");

            events = new ArrayList<>();

            // loop through all events
            for (int i = 0; i < dataJsonArr.length(); i++) {

                JSONObject event = dataJsonArr.getJSONObject(i);

                // Storing each json item in variable
                long id = event.getLong("Id");
                //get the date
                String dateStr = event.getString("Date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(dateStr);

                String name = event.getString("Name");

                events.add(new Evenement(Long.toString(id), name, date));

            }

            return events;

        }
        catch (Exception e)
        {
            Log.e("lol", "not working");
        }

        return null;
    }

    public ArrayList<Evenement> getEvents() {
        return events;
    }
}
