package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BETUL MURA on 28.1.2018.
 */

public class ListViewAdapter extends BaseAdapter{
    public ArrayList<HashMap<String,String>> list;
    Activity activity;

    public static final String FIRST_COLUMN="Patient_Name";
    public static final String SECOND_COLUMN="Patient_Tc";

    public ListViewAdapter(Activity activity,ArrayList<HashMap<String,String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater= activity.getLayoutInflater();
        if(convertView== null){
            convertView=inflater.inflate(R.layout.patient_list,null);
            holder=new ViewHolder();

            holder.txtFirst=(TextView) convertView.findViewById(R.id.TextFirst);
            holder.txtSecond=(TextView) convertView.findViewById(R.id.TextSecond);

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();

        }
        HashMap<String,String> map=list.get(position);
        holder.txtFirst.setText(map.get(FIRST_COLUMN));
        holder.txtSecond.setText(map.get(SECOND_COLUMN));

        return convertView;

    }

    private class ViewHolder{
        TextView txtFirst;
        TextView txtSecond;

    }
}
