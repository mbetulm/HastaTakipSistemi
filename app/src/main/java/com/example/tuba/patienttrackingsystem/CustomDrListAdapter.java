package com.example.tuba.patienttrackingsystem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by BETUL MURA on 13.3.2018.
 */

public class CustomDrListAdapter extends BaseAdapter {

    private Context mcontext;
    private List<DoctorMyAppointment> appointmentList;

    public CustomDrListAdapter(Context mcontext, List<DoctorMyAppointment> appointmentList) {
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
        View v = View.inflate(mcontext, R.layout.doctor_appointment_listview_row, null);
        TextView patientTc = (TextView)v.findViewById(R.id.txt_app_patientTc);
        TextView patientName = (TextView)v.findViewById(R.id.txt_app_patientName);
        TextView appDateTime = (TextView)v.findViewById(R.id.txt_app_datetime);

        patientTc.setText("   "+appointmentList.get(i).patientTc);
        patientName.setText("   "+appointmentList.get(i).patientName);
        appDateTime.setText("   "+appointmentList.get(i).appointmentDate + "," + appointmentList.get(i).appointmentTime);

        return v;
    }
}
