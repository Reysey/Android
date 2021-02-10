package com.reydev.tuto.androiddatabase;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PharmacieListViewAdapter extends ArrayAdapter<Pharmacie> {

    // VARIABLE DECLARATIONS
    //----------------------

    // ArrayList Of Pharmacies.
    private ArrayList<Pharmacie> pharmacies;


    /**
     * PharmacieListViewAdapter Constructor
     * @param context The current context. This value cannot be null.
     * @param resource The resource ID for a layout file containing a TextView to use when instantiating views.
     * @param obj ArrayList Of Pharmacies.
     */
    public PharmacieListViewAdapter(@NonNull Context context, int resource, ArrayList<Pharmacie> obj) {
        super(context, resource, obj);
        this.pharmacies = obj;
    }

    /**
     *
     * @param position     The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView  This value may be null.
     * @param parent       This value cannot be null.
     * @return VIEW, This value cannot be null.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Log.e("####### --LOG-- #######","Get View Called!");

        // CHECK IF WE HAVE A VIEW PASSED
        if(convertView == null){
            // CREATE A NEW VIEW.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_item_container, parent, false);
        }

        // PhName
         TextView PhName = convertView.findViewById(R.id.item_name);

        // PhFix
//         PhFix = convertView.findViewById(R.id.);

        // Distance
        TextView dst = convertView.findViewById(R.id.lv_dist);

        // PhAddress
         TextView PhAddress = convertView.findViewById(R.id.item_address);


        // PhCity
//         PhCity = convertView.findViewById(R.id.);

        // PhCountry
//         PhCountry = convertView.findViewById(R.id.);

        // PhLatitude
//         PhLatitude = convertView.findViewById(R.id.);

        // PhLongitude
//         PhLongitude = convertView.findViewById(R.id.);


        PhName.setText(pharmacies.get(position).getPhName());

        PhAddress.setText(pharmacies.get(position).getPhAddress());

        dst.setText("~"+Integer.toString((int) pharmacies.get(position).getPhDistance())+"m");


        return convertView;
    }
}
