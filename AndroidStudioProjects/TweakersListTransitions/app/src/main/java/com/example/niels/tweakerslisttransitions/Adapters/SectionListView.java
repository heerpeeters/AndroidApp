package com.example.niels.tweakerslisttransitions.Adapters;

import android.app.ListActivity;
import android.os.Bundle;

public class SectionListView extends ListActivity {

    private ShiftCategorieAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ShiftCategorieAdapter(this);
        
        setListAdapter(mAdapter);
    }

}