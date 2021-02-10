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

public class TwoItemsListViewAdapter extends ArrayAdapter<TitleAndDescription> {

    private ArrayList<TitleAndDescription> TAD_object;

    public TwoItemsListViewAdapter(@NonNull Context context, int resource, ArrayList<TitleAndDescription> ogj) {
        super(context, resource, ogj);
        this.TAD_object = ogj;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("--- getView", "GetView Started");
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview_contents,
                    parent, false);
        }

        TextView tv_title = convertView.findViewById(R.id.RD_content_textview_title);
        TextView tv_desc  = convertView.findViewById(R.id.RD_content_textview_desc);

        tv_title.setText(TAD_object.get(position).getTAD_Title());
        tv_desc.setText(TAD_object.get(position).getTAD_Description());

        return convertView;
    }
}
