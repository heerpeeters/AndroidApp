package com.example.niels.tweakerslisttransitions;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niels.tweakerslisttransitions.Adapters.EvenementAdapter;
import com.example.niels.tweakerslisttransitions.Evenementen.Evenement;
import com.example.niels.tweakerslisttransitions.Evenementen.EvenementenData;
import com.example.niels.tweakerslisttransitions.Evenementen.ShiftCategorie;
import com.example.niels.tweakerslisttransitions.Persistence.APIHandler;
import com.example.niels.tweakerslisttransitions.Persistence.AddEventHandler;
import com.example.niels.tweakerslisttransitions.Persistence.DeleteEventHandler;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ShiftCategorieActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link EvenementListFragment} and the item details
 * (if present) is a {@link ShiftCategorieDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link EvenementListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class EvenementListActivity extends ListActivity {

    private boolean mTwoPane;

    private EvenementAdapter mAdapter;

    private ArrayList<Evenement> events;

    private APIHandler handler;

    private SwipeRefreshLayout swipeContainer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new APIHandler();

        handler.doQuery();

        try {
            handler.get();
        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        events = handler.getEvents();

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.event_add_button, null);

        setContentView(R.layout.lijst);

        //enables pull to refresh
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                swipeContainer.setRefreshing(true);

                reloadData();

            }

        });

        //sets swipecontainer colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        getListView().addFooterView(v);

        mAdapter = new EvenementAdapter(this);

        for (Evenement event : events) {

            mAdapter.addSectionHeaderItem(event);

        }

        mAdapter.setmData(events);

        final ListActivity activity = this;

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

                                                 {

                                                     public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                                                                    int index, long arg3) {

                                                         View textView = v.findViewById(R.id.textSeparator);

                                                         final String id = (String)textView.getTag();

                                                         AlertDialog.Builder alert = new AlertDialog.Builder(activity);

                                                         alert.setTitle("Evenement verwijderen");
                                                         alert.setMessage("U staat op het punt een evenement te verwijderen. Bent u zeker dat u het evenement wil verwijderen?");

                                                         alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                             public void onClick(DialogInterface dialog, int whichButton) {

                                                                 deleteEvent(id);
                                                                 reloadData();

                                                             }
                                                         });

                                                         alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                             public void onClick(DialogInterface dialog, int whichButton) {

                                                                 return;

                                                             }
                                                         });

                                                         alert.show();

                                                         return true;
                                                     }

                                                 }

        );

        //Don't allow refreshing if we are not at the top of the list
        getListView().setOnScrollListener(new android.widget.AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                ListView listView = getListView();

                boolean enable = false;
                if (listView != null && listView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeContainer.setEnabled(enable);
            }
        });

        setListAdapter(mAdapter);

        // TODO: If exposing deep links into your app, handle intents here.
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onResume() {

        super.onResume();
        mAdapter.clearData();
        loadData();
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Intent shiftCategoryIntent = new Intent(this, ShiftCategorieActivity.class);
        shiftCategoryIntent.putExtra("id", mAdapter.getItem(position).getId());
        startActivity(shiftCategoryIntent);

    }

    //possibly obsolete
    private void loadData() {

        for (Evenement event : events) {
            mAdapter.addSectionHeaderItem(event);
        }

    }

    public void reloadData() {


        handler = new APIHandler();

        handler.doQuery();

        try {
            handler.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        mAdapter.clearData();

        ArrayList<Evenement> newEvents = handler.getEvents();
        for (Evenement event : newEvents) {

            mAdapter.addSectionHeaderItem(event);

        }

        mAdapter.setmData(newEvents);

        mAdapter.notifyDataSetChanged();
        setListAdapter(mAdapter);

        swipeContainer.setRefreshing(false);

    }

    public void voegEvenementToe(View v) {

        final View view = v;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Evenement toevoegen");
        alert.setMessage("Zet hier de naam van het evenement. Vervolgens zal u de datum van het evenement moeten kiezen");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                try {

                    DatePickerFragment newFragment = new DatePickerFragment();

                    AddEventHandler handler = new AddEventHandler();

                    newFragment.setHandler(handler);
                    newFragment.setName(value);

                    newFragment.show(getFragmentManager(), "Dag kiezen");

                    reloadData();
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();

    }

    public void deleteEvent(String id) {

        DeleteEventHandler handler = new DeleteEventHandler(id);

        handler.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        this.reloadData();

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EvenementList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.niels.tweakerslisttransitions/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "EvenementList Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.niels.tweakerslisttransitions/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
