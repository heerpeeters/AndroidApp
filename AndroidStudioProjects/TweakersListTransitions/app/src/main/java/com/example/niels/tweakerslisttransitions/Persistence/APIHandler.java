package com.example.niels.tweakerslisttransitions.Persistence;

import android.os.AsyncTask;
import android.util.Log;

import com.example.niels.tweakerslisttransitions.Evenementen.Evenement;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by niels on 15/06/2016.
 */
public class APIHandler extends AsyncTask {

    private String apiUrl = "http://shiftmanagerapi.azurewebsites.net/api/shift";

    private String test = null;

    private JSONArray dataJsonArr = null;

    private ArrayList<Evenement> events;

    public void doQuery()
    {

        this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        try {

            JsonParser jParser = new JsonParser();

            // get json string from url
            //JSONObject json = jParser.getJSONFromUrl(apiUrl);

            apiUrl = "http://shiftmanagerapi.azurewebsites.net/api/shift/GetEvents";

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
