package com.example.niels.tweakerslisttransitions;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.niels.tweakerslisttransitions.Evenementen.Evenement;
import com.example.niels.tweakerslisttransitions.Evenementen.EvenementenData;
import com.example.niels.tweakerslisttransitions.Evenementen.Shift;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link EvenementListActivity}
 * in two-pane mode (on tablets) or a {@link EvenementDetailActivity}
 * on handsets.
 */
public class EvenementDetailFragment extends ListFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Evenement evenement;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EvenementDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            evenement = EvenementenData.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            //git test

            setListAdapter(new ArrayAdapter<Shift>(
                    getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    android.R.id.text1,
                    evenement.getLijst()));


        }
    }

}
