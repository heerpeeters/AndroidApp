package com.example.niels.tweakerslisttransitions;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

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

    private List medewerkers;

    private MedewerkerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();

        Shift s = EvenementenData.getShiftById(intent.getStringExtra("id"));

        mAdapter = new MedewerkerAdapter(this);
        for(Medewerker m : s.getMedewerkers()){
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
}


