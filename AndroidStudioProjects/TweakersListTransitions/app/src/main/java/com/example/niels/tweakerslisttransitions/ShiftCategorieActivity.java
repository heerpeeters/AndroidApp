package com.example.niels.tweakerslisttransitions;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niels.tweakerslisttransitions.Adapters.ShiftCategorieAdapter;
import com.example.niels.tweakerslisttransitions.Evenementen.Evenement;
import com.example.niels.tweakerslisttransitions.Evenementen.EvenementenData;
import com.example.niels.tweakerslisttransitions.Evenementen.Medewerker;
import com.example.niels.tweakerslisttransitions.Evenementen.Shift;
import com.example.niels.tweakerslisttransitions.Evenementen.ShiftCategorie;
import com.example.niels.tweakerslisttransitions.Evenementen.iShift;
import com.example.niels.tweakerslisttransitions.Fragments.TimePickerFragment;
import android.app.DialogFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link EvenementListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ShiftCategorieDetailFragment}.
 */
public class ShiftCategorieActivity extends ListActivity {

    private ShiftCategorieAdapter mAdapter;

    public static String ARG_EVENT_ID;

    private Evenement evenement;

    Intent intent;

    private ListView list;

    private ShiftCategorie shiftCategorieSelected;

    Bundle savedInstanceState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;

        final ListActivity activity = this;

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.shift_category_add_button, null);

        setContentView(R.layout.shiftcategory_list);

        getListView().addFooterView(v);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                //check if the long clicked item is shiftcategorie or shift
                if(mAdapter.getItem(position) instanceof ShiftCategorie)
                {

                    ((ShiftCategorie) mAdapter.getItem(position)).sortShifts();

                    final int positionToDelete = position;

                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                    alert.setTitle("Shiftcategorie verwijderen");
                    alert.setMessage("U staat op het punt een shiftcategorie te verwijderen. Als u doorgaat verwijdert u ook alle onderliggende shifts");

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            evenement.getLijst().remove(mAdapter.getItem(positionToDelete));

                            reloadData();
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            return;

                        }

                    });

                    alert.show();


                }
                //If it is a shiftitem, get the shiftcategorie first and then delete the shift on it
                else
                {

                    final int positionToDelete = position;

                    AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                    alert.setTitle("Shift verwijderen");
                    alert.setMessage("U staat op het punt een shift te verwijderen. Als u doorgaat verwijdert u ook de onderliggende medewerkers");

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            mAdapter.getShiftCategorieForPosition(positionToDelete).getShiften().remove(mAdapter.getItem(positionToDelete));

                            reloadData();
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            return;

                        }

                    });

                    alert.show();


                }

                reloadData();
                return true;

            }
        });


        intent = getIntent();

        evenement = EvenementenData.ITEM_MAP.get(intent.getStringExtra("id"));;

        mAdapter = new ShiftCategorieAdapter(this);

        if(evenement.getLijst() == null || evenement.getLijst().isEmpty())
            return;
        else
        {

        loadData();

        setListAdapter(mAdapter);

        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

        super.onListItemClick(l, v, position, id);

        //Als het item een shiftcategorie is, doe dan niets
        if(mAdapter.getItem(position) instanceof ShiftCategorie)
            return;

        Intent shiftDetailIntent = new Intent(this, ShiftDetailActivity.class);
        shiftDetailIntent.putExtra("id", mAdapter.getItem(position).getId());
        shiftDetailIntent.putExtra("categorieId", mAdapter.getShiftCategorieForPosition(position).getId());
        shiftDetailIntent.putExtra("eventId", evenement.getId());
        startActivity(shiftDetailIntent);

    }

    private void loadData(){


        for(ShiftCategorie sc : evenement.getLijst()){
            mAdapter.addSectionHeaderItem(sc);

            for(Shift s : sc.getShiften()){

                mAdapter.addItem(s);

            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, EvenementListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){

        super.onResume();
        mAdapter.clearData();
        loadData();
        mAdapter.notifyDataSetChanged();
        setListAdapter(mAdapter);

    }

    //this method reloads data to show new contents of list when things are changed
    public void reloadData(){

        mAdapter.clearData();
        for(ShiftCategorie sc : evenement.getLijst()){

            mAdapter.addSectionHeaderItem(sc);

            sc.sortShifts();

            for(Shift s : sc.getShiften()){

                mAdapter.addItem(s);

            }

        }
        mAdapter.notifyDataSetChanged();
        setListAdapter(mAdapter);

    }

    public void voegShiftCategorieToe(View v){

        final View view = v;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Shiftcategorie toevoegen");
        alert.setMessage("Zet hier de naam van de shiftcategorie");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                try {
                    evenement.voegShiftCategorieToe(new ShiftCategorie(Integer.toString(evenement.getLijst().size()), value));
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
                return;
            }
        });

        alert.show();



    }

    public void voegShiftToe(View v)
    {

        final View view = v;

        //this fragment loads both start hour of the shift and end hour and will create the new shift
        TimePickerFragment timePickerBeginuur = new TimePickerFragment();
        timePickerBeginuur.setTitle("Beginuur");
        //get the shiftcategorie and pass it to the timepickerfragment
        this.setShiftCategorieSelected(mAdapter.getShiftCategorieForPosition((int) v.getTag()));
        //set the activity in order to reload the data.
        timePickerBeginuur.setShiftCategorieActivity(this);
        timePickerBeginuur.show(this.getFragmentManager(), "Beginuur");


    }

    public void addShift(int beginuur, int beginminuut, int einduur, int eindminuut, int medewerkers)
    {

        getShiftCategorieSelected().addShift(beginuur, beginminuut, einduur, eindminuut, medewerkers);
        getShiftCategorieSelected().sortShifts();
        finish();
        startActivity(getIntent());

    }

    public ShiftCategorie getShiftCategorieSelected() {
        return shiftCategorieSelected;
    }

    public void setShiftCategorieSelected(ShiftCategorie shiftCategorieSelected) {
        this.shiftCategorieSelected = shiftCategorieSelected;
    }
}
