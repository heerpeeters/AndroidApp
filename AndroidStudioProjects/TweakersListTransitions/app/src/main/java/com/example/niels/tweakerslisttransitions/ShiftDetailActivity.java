package com.example.niels.tweakerslisttransitions;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niels.tweakerslisttransitions.Adapters.MedewerkerAdapter;
import com.example.niels.tweakerslisttransitions.Adapters.ShiftCategorieAdapter;
import com.example.niels.tweakerslisttransitions.Evenementen.EvenementenData;
import com.example.niels.tweakerslisttransitions.Evenementen.Medewerker;
import com.example.niels.tweakerslisttransitions.Evenementen.Shift;
import com.example.niels.tweakerslisttransitions.Evenementen.ShiftCategorie;

import java.util.List;

/**
 * Created by Niels on 3/06/2015.
 */
public class ShiftDetailActivity extends ListActivity{

    private Intent intent;

    private MedewerkerAdapter mAdapter;

    private Shift s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lijst);

        intent = getIntent();

        s = EvenementenData.getShiftById(intent.getStringExtra("eventId"),intent.getStringExtra("id"));

        mAdapter = new MedewerkerAdapter(this);

        mAdapter.setMaxMedewerkers(s.getAantalMedewerkersNodig());

        for (Medewerker m : s.getMedewerkers()) {
            mAdapter.addItem(m);
        }

        setListAdapter(mAdapter);
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
            NavUtils.navigateUpTo(this, new Intent(this, ShiftCategorieActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //use this method when adding or deleting content
    public void reloadData(){

        mAdapter.clearData();
        for(Medewerker m : s.getMedewerkers()){

            mAdapter.addItem(m);

        }
        mAdapter.notifyDataSetChanged();
        setListAdapter(mAdapter);

    }

    public void voegMedewerkerToe(View v){

        final View view = v;

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Medewerker toevoegen");
        alert.setMessage("Zet hier de naam van de medewerker");

// Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                try {
                    s.voegMedewerkerToe(new Medewerker(value));
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

    public void verwijderMedewerker(View v){

        s.verwijderMedewerker((Integer) v.getTag());
        reloadData();

    }
}


