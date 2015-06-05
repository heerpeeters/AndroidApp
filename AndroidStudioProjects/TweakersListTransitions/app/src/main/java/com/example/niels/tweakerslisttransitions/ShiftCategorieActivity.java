package com.example.niels.tweakerslisttransitions;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.niels.tweakerslisttransitions.Adapters.ShiftCategorieAdapter;
import com.example.niels.tweakerslisttransitions.Evenementen.Evenement;
import com.example.niels.tweakerslisttransitions.Evenementen.EvenementenData;
import com.example.niels.tweakerslisttransitions.Evenementen.Shift;
import com.example.niels.tweakerslisttransitions.Evenementen.ShiftCategorie;
import com.example.niels.tweakerslisttransitions.Evenementen.iShift;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = getIntent();

        evenement = EvenementenData.ITEM_MAP.get(intent.getStringExtra("id"));

        mAdapter = new ShiftCategorieAdapter(this);
        for(ShiftCategorie sc : evenement.getLijst()){
            mAdapter.addSectionHeaderItem(sc);
            /*list = (ListView)findViewById(R.id.text);
            list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(this, ShiftDetailActivity.class);
                }
            });*/
            for(Shift s : sc.getShiften()){

                mAdapter.addItem(s);

            }
        }
        setListAdapter(mAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

        super.onListItemClick(l, v, position, id);
        Intent shiftDetailIntent = new Intent(this, ShiftDetailActivity.class);
        shiftDetailIntent.putExtra("id", mAdapter.getItem(position).getId());
        startActivity(shiftDetailIntent);

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
}
