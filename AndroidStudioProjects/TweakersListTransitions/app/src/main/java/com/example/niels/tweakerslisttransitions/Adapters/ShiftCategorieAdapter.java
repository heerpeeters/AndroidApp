package com.example.niels.tweakerslisttransitions.Adapters;

import java.util.ArrayList;
import java.util.TreeSet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.niels.tweakerslisttransitions.Evenementen.iShift;
import com.example.niels.tweakerslisttransitions.R;

public class ShiftCategorieAdapter extends BaseAdapter {

    //Deze adapter zorgt voor de uitvoer van de lijst met shiftcategories

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<iShift> mData = new ArrayList<>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public ShiftCategorieAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ShiftCategorieAdapter(Context context, ArrayList<iShift> categories) {

        mData = categories;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final iShift item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final iShift item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public iShift getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clearData(){

        mData = new ArrayList<>();

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:

                        convertView = mInflater.inflate(R.layout.snippet_item1, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.text);
                    break;

                case TYPE_SEPARATOR:
                    //when the shiftcategory is the last in the list, add an add button
                        convertView = mInflater.inflate(R.layout.snippet_item2, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);

                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position).toString());


        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }

}
