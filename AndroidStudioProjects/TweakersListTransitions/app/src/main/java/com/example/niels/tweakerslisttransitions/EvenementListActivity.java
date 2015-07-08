package com.example.niels.tweakerslisttransitions;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.niels.tweakerslisttransitions.Adapters.EvenementAdapter;
import com.example.niels.tweakerslisttransitions.Evenementen.Evenement;
import com.example.niels.tweakerslisttransitions.Evenementen.EvenementenData;

import java.util.List;


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

        loadData();

        setListAdapter(mAdapter);
        /*
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((EvenementListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.item_list))
                    .setActivateOnItemClick(true);
        }
        */
        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link EvenementListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    /*@Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ShiftCategorieDetailFragment.ARG_ITEM_ID, id);
            ShiftCategorieDetailFragment fragment = new ShiftCategorieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ShiftCategorieActivity.class);
            //detailIntent.putExtra(ShiftCategorieActivity.ARG_EVENT_ID, id);
            detailIntent.putExtra("id", id);
            detailIntent.putExtra(ShiftCategorieDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }*/

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

        for(Evenement event : EvenementenData.ITEMS)
            mAdapter.addSectionHeaderItem(event);

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
                    List<Evenement> evenementen = EvenementenData.ITEMS;
                    int id = Integer.parseInt(evenementen.get(evenementen.size() - 1).getId()) + 1;
                    evenementen.add(new Evenement(Integer.toString(id), value));
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
}
