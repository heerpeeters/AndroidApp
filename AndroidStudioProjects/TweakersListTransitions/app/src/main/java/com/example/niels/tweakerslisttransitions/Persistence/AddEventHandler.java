package com.example.niels.tweakerslisttransitions.Persistence;

import android.os.AsyncTask;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by niels on 19/06/2016.
 */
public class AddEventHandler extends AsyncTask{

    private String apiUrl = "http://shiftmanagerapi.azurewebsites.net/api/shift";
    String name;
    String date;

    public void doQuery(String name, DateTime date)
    {

        this.name = name;

        DateTime dt = new DateTime(date);

        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

        this.date = fmt.print(dt);
        //this.date = dt.getYear() + "-" + dt.getMonthOfYear() + dt.getDayOfMonth();

        this.execute();

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        apiUrl = String.format("http://shiftmanagerapi.azurewebsites.net/api/shift/AddEvent/?name=%s&date=%s", name, date);

        InputStream in = null;

        HttpURLConnection urlConnection;

        try {

            URL url = new URL(apiUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            in = urlConnection.getInputStream();

            urlConnection.disconnect();

        } catch (Exception e) {

            System.out.println(e.getMessage());


        }

        return null;

    }
}
