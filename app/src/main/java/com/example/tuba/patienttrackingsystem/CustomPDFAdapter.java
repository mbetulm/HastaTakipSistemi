package com.example.tuba.patienttrackingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by BETUL MURA on 4.4.2018.
 */

public class CustomPDFAdapter extends ArrayAdapter<String> {

    public CustomPDFAdapter(Context context, ArrayList<String> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.pdf_txt);
        ImageView image = (ImageView)convertView.findViewById(R.id.image);


        // Populate the data into the template view using the data object
        tvName.setText(user);
        image.setImageResource(R.drawable.pdf);

        // Return the completed view to render on screen
        return convertView;
    }
}
