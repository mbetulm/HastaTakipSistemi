package com.example.tuba.patienttrackingsystem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by BETUL MURA on 29.3.2018.
 */

public class CustomDrRelationReportListAdapter extends BaseAdapter {

    private Context mcontext;
    private List<DoctorGetRelationReport> relationReportList;
    private int PositionSelected = 0;

    public CustomDrRelationReportListAdapter(Context mcontext, List<DoctorGetRelationReport> relationReportList) {
        this.mcontext = mcontext;
        this.relationReportList = relationReportList;

    }

    @Override
    public int getCount() {
        return relationReportList.size();
    }

    @Override
    public Object getItem(int i) {
        return relationReportList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setPositionSelected(int position)
    {
        PositionSelected = position;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mcontext, R.layout.doctor_relationreport_listview_row, null);
        TextView systemDate = (TextView)v.findViewById(R.id.txt_systemDate);
        TextView patientTc = (TextView)v.findViewById(R.id.txt_patientTc);
        TextView patientName = (TextView)v.findViewById(R.id.txt_patientName);
        TextView diseaseTime = (TextView)v.findViewById(R.id.txt_diseaseName);
        TextView startTrack = (TextView)v.findViewById(R.id.txt_startTrack);

        systemDate.setText("   "+relationReportList.get(i).systemDate);
        patientTc.setText("   "+relationReportList.get(i).patient_tc);
        patientName.setText("   "+relationReportList.get(i).patient_name);
        diseaseTime.setText("   "+relationReportList.get(i).disease_name);
        startTrack.setText(relationReportList.get(i).start_track);
        startTrack.setEnabled(false);

        return v;
    }


}
