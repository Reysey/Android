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

public class DocteurListViewAdapter extends ArrayAdapter<Docteur> {

// VARIABLE DECLARATIONS
//----------------------

// ArrayList OfDocteurs.
private ArrayList<Docteur>Docteurs;


        /**
         *DocteurListViewAdapter Constructor
         * @param context The current context. This value cannot be null.
         * @param resource The resource ID for a layout file containing a TextView to use when instantiating views.
         * @param obj ArrayList OfDocteurs.
         */
        public DocteurListViewAdapter(@NonNull Context context, int resource, ArrayList<Docteur> obj) {
            super(context, resource, obj);
            this.Docteurs = obj;
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_doc_listview_item_container, parent, false);
            }

            // DocName
            TextView DocName = convertView.findViewById(R.id.item_docname);

            // DocFix
            // DocFix = convertView.findViewById(R.id.);

            // Distance
            TextView dst = convertView.findViewById(R.id.lv_docdist);

            // DocAddress
            TextView DocAddress = convertView.findViewById(R.id.item_docaddress);


            // DocCity
            //  DocCity = convertView.findViewById(R.id.);

            // DocCountry
            //         DocCountry = convertView.findViewById(R.id.);

            // DocLatitude
            //         DocLatitude = convertView.findViewById(R.id.);

            // DocLongitude
    //         DocLongitude = convertView.findViewById(R.id.);

            //Log.e("GETVIEW", "GETVIEW || Docteurs.get(position).getDocName() || VALUE: ["+Docteurs.get(position).getDocName()+"]");
            DocName.setText(Docteurs.get(position).getDocName());

            DocAddress.setText(Docteurs.get(position).getDocAddress());

            dst.setText("~"+Integer.toString((int)Docteurs.get(position).getDocDistance())+"m");



            return convertView;
        }
}
