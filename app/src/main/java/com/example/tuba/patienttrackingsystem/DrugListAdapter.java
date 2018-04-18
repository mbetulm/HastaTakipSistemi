package com.example.tuba.patienttrackingsystem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BETUL MURA on 3.4.2018.
 */

public class DrugListAdapter extends BaseAdapter {

    private Context mcontext;
    private List<PatientMyDrugs> drugList;

    public DrugListAdapter(Context mcontext, List<PatientMyDrugs> drugList) {
        this.mcontext = mcontext;
        this.drugList = drugList;
    }

    @Override
    public int getCount() {
        return drugList.size();
    }

    @Override
    public Object getItem(int i) {
        return drugList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mcontext, R.layout.patient_drug_listview_row, null);
        TextView drugName1 = (TextView) v.findViewById(R.id.txt_drugName1);
        TextView drugName2 = (TextView) v.findViewById(R.id.txt_drugName2);
        TextView drugName3 = (TextView) v.findViewById(R.id.txt_drugName3);
        TextView drugName4 = (TextView) v.findViewById(R.id.txt_drugName4);
        TextView drugName5 = (TextView) v.findViewById(R.id.txt_drugName5);

        drugName1.setText((drugList.get(i).drugName1));
        drugName2.setText((drugList.get(i).drugName2));
        drugName3.setText((drugList.get(i).drugName3));
        drugName4.setText((drugList.get(i).drugName4));
        drugName5.setText((drugList.get(i).drugName5));

        return v;
    }
}
