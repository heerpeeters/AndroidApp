package com.example.niels.tweakerslisttransitions;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niels.tweakerslisttransitions.Adapters.EvenementAdapter;
import com.example.niels.tweakerslisttransitions.Evenementen.Evenement;
import com.example.niels.tweakerslisttransitions.Evenementen.EvenementenData;
import com.example.niels.tweakerslisttransitions.Evenementen.ShiftCategorie;

import java.util.List;
import java.util.Map;


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

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private EvenementAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.event_add_button, null);

        setContentView(R.layout.lijst);

        getListView().addFooterView(v);

        mAdapter = new EvenementAdapter(this);

        final ListActivity activity = this;

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

                                                 {

                                                     public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                                                                    int index, long arg3) {

                                                         final int plaats = index;

                                                         AlertDialog.Builder alert = new AlertDialog.Builder(activity);

                                                         alert.setTitle("Evenement verwijderen");
                                                         alert.setMessage("U staat op het punt een evenement te verwijderen. Bent u zeker dat u het evenement wil verwijderen?");

                                                         alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                             public void onClick(DialogInterface dialog, int whichButton) {

                                                                 deleteEvent(plaats);
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

        loadData();

        setListAdapter(mAdapter);

        // TODO: If exposing deep links into your app, handle intents here.
    }


    @Override
    protected void onResume(){

        super.onResume();
        mAdapter.clearData();
        loadData();
        mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

        super.onListItemClick(l, v, position, id);

        Intent shiftCategoryIntent = new Intent(this, ShiftCategorieActivity.class);
        shiftCategoryIntent.putExtra("id", mAdapter.getItem(position).getId());
        startActivity(shiftCategoryIntent);

    }


    private void loadData() {

        EvenementenData.orderEventsByDate();

        for(Map.Entry<String, Evenement> event: EvenementenData.ITEM_MAP.entrySet())
            mAdapter.addSectionHeaderItem(event.getValue());

    }
    public void reloadData(){

        mAdapter.clearData();
        loadData();
        mAdapter.notifyDataSetChanged();
        setListAdapter(mAdapter);
    }

    public void voegEvenementToe(View v){

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
                    Map<String, Evenement> evenementen = EvenementenData.ITEM_MAP;
                    int id = Integer.parseInt(evenementen.get(Integer.toString(evenementen.size())).getId()) + 1;
                    EvenementenData.ITEM_MAP.put(Integer.toString(id), new Evenement(Integer.toString(id), value));
                    reloadData();
                    mAdapter.notifyDataSetChanged();
                    DatePickerFragment newFragment = new DatePickerFragment();
                    newFragment.setId(id);
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

    public void deleteEvent(int id)
    {

        EvenementenData.deleteEvent(id);

    }
}
