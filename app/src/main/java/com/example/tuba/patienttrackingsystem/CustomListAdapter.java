package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BETUL MURA on 13.3.2018.
 */

public class CustomListAdapter extends BaseAdapter {
    private Context mcontext;
    private List<PatientMyAppointment> appointmentList;

    public CustomListAdapter(Context mcontext, List<PatientMyAppointment> appointmentList) {
        this.mcontext = mcontext;
        this.appointmentList = appointmentList;
    }

    @Override
    public int getCount() {
        return appointmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return appointmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(mcontext, R.layout.patient_appointment_listview_row, null);
        TextView doctorName = (TextView)v.findViewById(R.id.txt_app_doctorName);
        TextView departmentName = (TextView)v.findViewById(R.id.txt_app_depName);
        TextView appDateTime = (TextView)v.findViewById(R.id.txt_app_dateTime);

        doctorName.setText(appointmentList.get(i).doctorName);
        departmentName.setText(appointmentList.get(i).departmentName);
        appDateTime.setText(appointmentList.get(i).appointmentDate + "," + appointmentList.get(i).appointmentTime);

        return v;
    }
}
