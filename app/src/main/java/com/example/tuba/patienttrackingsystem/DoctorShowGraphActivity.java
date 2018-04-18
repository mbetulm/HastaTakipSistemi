package com.example.tuba.patienttrackingsystem;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DoctorShowGraphActivity extends AppCompatActivity {

    GraphView graph1, graph2, graph3 ;
    int morningValues_sistol[] = new int[DoctorGetPatientFullTrackReport.trackValues.length];
    int morningValues_diastol[] = new int[DoctorGetPatientFullTrackReport.trackValues.length];

    int afternoonValues_sistol[] = new int[DoctorGetPatientFullTrackReport.trackValues.length];
    int afternoonValues_diastol[] = new int[DoctorGetPatientFullTrackReport.trackValues.length];

    int eveningValues_sistol[] = new int[DoctorGetPatientFullTrackReport.trackValues.length];
    int eveningValues_diastol[] = new int[DoctorGetPatientFullTrackReport.trackValues.length];

    int morningValues_weight[];

    DataPoint values[];
    DataPoint values2[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_show_graph);

        if (DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("1") || DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("2")){
            for (int i =0; i < DoctorGetPatientFullTrackReport.trackValues.length ; i++ ){
                String part1[] = DoctorGetPatientFullTrackReport.trackValues[i][1].split("-");
                String value1=part1[0];
                morningValues_sistol[i]= Integer.parseInt(value1);
                String value2=part1[1];
                morningValues_diastol[i]=Integer.parseInt(value2);

                String part2[] = DoctorGetPatientFullTrackReport.trackValues[i][2].split("-");
                String value3=part2[0];
                afternoonValues_sistol[i]= Integer.parseInt(value3);
                String value4=part2[1];
                afternoonValues_diastol[i]=Integer.parseInt(value4);

                String part3[] = DoctorGetPatientFullTrackReport.trackValues[i][3].split("-");
                String value5=part3[0];
                eveningValues_sistol[i]= Integer.parseInt(value5);
                String value6=part3[1];
                eveningValues_diastol[i]=Integer.parseInt(value6);
            }

            graph1 = (GraphView)findViewById(R.id.graphProgression1);
            drawGraph();

            graph2 = (GraphView)findViewById(R.id.graphProgression2);
            drawGraphAfternoon();

            graph3 = (GraphView)findViewById(R.id.graphProgression3);
            drawGraphEvening();

        }else if (DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("3")){

            for (int i =0; i < DoctorGetPatientFullTrackReport.trackValues.length ; i++ ){
                morningValues_sistol[i]= (int)Math.round(Double.parseDouble(DoctorGetPatientFullTrackReport.trackValues[i][1]));
                afternoonValues_sistol[i]= (int)Math.round(Double.parseDouble(DoctorGetPatientFullTrackReport.trackValues[i][2]));
                eveningValues_sistol[i]= (int)Math.round(Double.parseDouble(DoctorGetPatientFullTrackReport.trackValues[i][3]));

            }

            graph1 = (GraphView)findViewById(R.id.graphProgression1);
            drawGraphHeartRate();

            graph2 = (GraphView)findViewById(R.id.graphProgression2);
            drawGraphAfternoonHeartRate();

            graph3 = (GraphView)findViewById(R.id.graphProgression3);
            drawGraphEveningHeartRate();

        }

    }

    public void drawGraph(){

        values = new DataPoint[morningValues_sistol.length];
        for (int j =0 ; j< morningValues_sistol.length; j++){
            DataPoint v = new DataPoint(j, morningValues_sistol[j]);
            values[j] = v;
        }

        values2 = new DataPoint[morningValues_diastol.length];
        for (int j =0 ; j< morningValues_diastol.length; j++){
            DataPoint v = new DataPoint(j, morningValues_diastol[j]);
            values2[j] = v;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setColor(Color.BLUE);
        graph1.addSeries(series);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(values2);
        series.setColor(Color.RED);
        graph1.addSeries(series2);


    }

    public void drawGraphAfternoon(){
        values = new DataPoint[afternoonValues_sistol.length];
        for (int j =0 ; j< afternoonValues_sistol.length; j++){
            DataPoint v = new DataPoint(j, afternoonValues_sistol[j]);
            values[j] = v;
        }

        values2 = new DataPoint[afternoonValues_diastol.length];
        for (int j =0 ; j< afternoonValues_diastol.length; j++){
            DataPoint v = new DataPoint(j, afternoonValues_diastol[j]);
            values2[j] = v;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setColor(Color.BLUE);
        graph2.addSeries(series);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(values2);
        series.setColor(Color.RED);
        graph2.addSeries(series2);
    }

    public void drawGraphEvening(){
        values = new DataPoint[eveningValues_sistol.length];
        for (int j =0 ; j< eveningValues_sistol.length; j++){
            DataPoint v = new DataPoint(j, eveningValues_sistol[j]);
            values[j] = v;
        }

        values2 = new DataPoint[eveningValues_diastol.length];
        for (int j =0 ; j< eveningValues_diastol.length; j++){
            DataPoint v = new DataPoint(j, eveningValues_diastol[j]);
            values2[j] = v;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setColor(Color.BLUE);
        graph3.addSeries(series);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(values2);
        series.setColor(Color.RED);
        graph3.addSeries(series2);
    }

    public void drawGraphHeartRate(){

        values = new DataPoint[morningValues_sistol.length];
        for (int j =0 ; j< morningValues_sistol.length; j++){
            DataPoint v = new DataPoint(j, morningValues_sistol[j]);
            values[j] = v;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setColor(Color.BLUE);
        graph1.addSeries(series);

    }

    public void drawGraphAfternoonHeartRate(){
        values = new DataPoint[afternoonValues_sistol.length];
        for (int j =0 ; j< afternoonValues_sistol.length; j++){
            DataPoint v = new DataPoint(j, afternoonValues_sistol[j]);
            values[j] = v;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setColor(Color.BLUE);
        graph2.addSeries(series);

    }

    public void drawGraphEveningHeartRate(){
        values = new DataPoint[eveningValues_sistol.length];
        for (int j =0 ; j< eveningValues_sistol.length; j++){
            DataPoint v = new DataPoint(j, eveningValues_sistol[j]);
            values[j] = v;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        series.setColor(Color.BLUE);
        graph3.addSeries(series);
    }

}
