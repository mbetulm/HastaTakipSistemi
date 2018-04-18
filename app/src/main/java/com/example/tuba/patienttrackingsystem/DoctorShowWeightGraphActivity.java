package com.example.tuba.patienttrackingsystem;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class DoctorShowWeightGraphActivity extends AppCompatActivity {

    GraphView graph1;
    int morningValues_weight[] = new int[DoctorGetPatientTrackReport.trackValues.length];;

    DataPoint values[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_show_weight_graph);

        if (DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("4")){

            for (int i =0; i < DoctorGetPatientTrackReport.trackValues.length ; i++ ){
                morningValues_weight[i]= (int)Math.round(Double.parseDouble(DoctorGetPatientTrackReport.trackValues[i][1]));
            }
            graph1 = (GraphView)findViewById(R.id.graphProgressionWeight);
            drawGraphWeight();

        }
    }

    public void drawGraphWeight(){

        values = new DataPoint[morningValues_weight.length];
        for (int j =0 ; j< morningValues_weight.length; j++){
            DataPoint v = new DataPoint(j, morningValues_weight[j]);
            values[j] = v;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setColor(Color.BLUE);
        graph1.addSeries(series);

    }
}
