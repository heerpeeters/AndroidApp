package com.example.niels.tweakerslisttransitions.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.niels.tweakerslisttransitions.Evenementen.Medewerker;
import com.example.niels.tweakerslisttransitions.Evenementen.iShift;
import com.example.niels.tweakerslisttransitions.R;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by Niels on 5/06/2015.
 */
public class MedewerkerAdapter extends BaseAdapter {

    //Deze adapter zorgt voor de uitvoer van de lijst met shiftcategories

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<Medewerker> mData = new ArrayList<>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;

    public MedewerkerAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public MedewerkerAdapter(Context context, ArrayList<Medewerker> categories) {

        mData = categories;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final Medewerker item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final Medewerker item) {
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
    public Medewerker getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    if((mData.size() - 1) == position){
                        convertView = mInflater.inflate(R.layout.shift_detail, null);
                        holder.textView = (TextView) convertView.findViewById(R.id.text);
                        holder.button = (Button) convertView.findViewById(R.id.row_add_button);
                        holder.button.setText("+");
                    }
                    else{
                    convertView = mInflater.inflate(R.layout.snippet_item1, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);}
                    break;
                case TYPE_SEPARATOR:
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
        public Button button;
    }



}
