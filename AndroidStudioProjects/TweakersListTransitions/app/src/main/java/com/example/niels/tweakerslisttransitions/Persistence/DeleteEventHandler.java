package com.example.niels.tweakerslisttransitions.Persistence;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by niels on 19/06/2016.
 */
public class DeleteEventHandler extends AsyncTask
{

    String id, apiUrl;

    public DeleteEventHandler(String id)
    {

        setId(id);

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        apiUrl = String.format("http://shiftmanagerapi.azurewebsites.net/api/shift/DeleteEvent/?id=%s", id);

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

    public void setId(String id) {
        this.id = id;
    }
}
