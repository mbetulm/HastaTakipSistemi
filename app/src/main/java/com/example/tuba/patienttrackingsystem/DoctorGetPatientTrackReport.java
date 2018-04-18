package com.example.tuba.patienttrackingsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class DoctorGetPatientTrackReport extends AppCompatActivity {

    String [] trackValuesHeaders={"Date","Value"};
    public static String[][] trackValues;
    ArrayList<TrackValues> trackValuesList = new ArrayList<>();
    String baseurl1 = "http://192.168.156.169/Service1.svc/DoctorGetTrackWeightReport";
    String baseurl2 = "http://192.168.156.169/Service1.svc/DoctorGetTrackAllergyReport";
    String system_date;
    static TableView<String[]> tb;
    TextView trackPatientName;
    String allergyScoreCondition;
    Button GraphButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_get_patient_track_report);

        tb = (TableView<String[]>) findViewById(R.id.tableView2);
        tb.setColumnCount(2);
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));

        trackPatientName = (TextView)findViewById(R.id.patientTrackName2);
        trackPatientName.setText(DoctorShowTrackActivity.showTrack_selectedPatient);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        system_date = (sdf2.format(calendar.getTime()));

        if (DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("4")){
            new GetTrackWeightReport().execute(baseurl1 + "/" + DoctorShowTrackActivity.showTrack_selectedPatientTc);

        }else if(DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("5")) {
            new GetTrackAllergyReport().execute(baseurl2 + "/" + DoctorShowTrackActivity.showTrack_selectedPatientTc);
        }

        GraphButton = (Button)findViewById(R.id.graphBtn);
        if (DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("5")){
            GraphButton.setVisibility(View.INVISIBLE);
        }
        GraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("4")){
                    Intent intent = new Intent(DoctorGetPatientTrackReport.this, DoctorShowWeightGraphActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    class GetTrackWeightReport extends AsyncTask<String, Void, String> {
        String status = null;

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn;
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();
                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                }

            } catch (Exception ex) {

                System.out.print(ex);


            }
            return status;
        }


        public void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String endTrack = jsonArray.getJSONObject(i).getString("End_Track");
                        String date = jsonArray.getJSONObject(i).getString("Date");
                        String morning_value = jsonArray.getJSONObject(i).getString("Value");

                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        if ( /*format.parse(DoctorShowTrackActivity.showTrack_selectedStart_Track).after(format.parse(date)) && format.parse(endTrack).before(format.parse(date))*/true) {
                            TrackValues trackValues=new TrackValues();

                            trackValues.setDate(date);
                            trackValues.setMorning(morning_value);
                            trackValuesList.add(trackValues);
                        }
                    }

                    trackValues = new String[trackValuesList.size()][4];
                    for (int i=0;i<trackValuesList.size();i++) {
                        TrackValues s = trackValuesList.get(i);
                        trackValues[i][0]=s.getDate();
                        trackValues[i][1]=s.getMorning();

                    }
                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(DoctorGetPatientTrackReport.this, trackValuesHeaders));
                    tb.setDataAdapter(new SimpleTableDataAdapter(DoctorGetPatientTrackReport.this, trackValues));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class GetTrackAllergyReport extends AsyncTask<String, Void, String> {
        String status = null;

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn;
            BufferedReader reader;
            try {
                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();
                if (result == 200) {
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        status = line;
                    }
                }

            } catch (Exception ex) {

                System.out.print(ex);


            }
            return status;
        }


        public void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String endTrack = jsonArray.getJSONObject(i).getString("End_Track");
                        String date = jsonArray.getJSONObject(i).getString("Date");
                        String morning_value = jsonArray.getJSONObject(i).getString("Value");

                        if(morning_value.equals("1") || morning_value.equals("2")){
                            allergyScoreCondition = "BAD";

                        }else if(morning_value.equals("3")){
                            allergyScoreCondition = "NORMAL";

                        }else if(morning_value.equals("4") || morning_value.equals("5")){
                            allergyScoreCondition = "GOOD";

                        }

                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        if ( /*format.parse(DoctorShowTrackActivity.showTrack_selectedStart_Track).after(format.parse(date)) && format.parse(endTrack).before(format.parse(date))*/true) {
                            TrackValues trackValues=new TrackValues();

                            trackValues.setDate(date);
                            trackValues.setMorning(allergyScoreCondition);
                            trackValuesList.add(trackValues);
                        }
                    }

                    trackValues = new String[trackValuesList.size()][4];
                    for (int i=0;i<trackValuesList.size();i++) {
                        TrackValues s = trackValuesList.get(i);
                        trackValues[i][0]=s.getDate();
                        trackValues[i][1]=s.getMorning();

                    }
                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(DoctorGetPatientTrackReport.this, trackValuesHeaders));
                    tb.setDataAdapter(new SimpleTableDataAdapter(DoctorGetPatientTrackReport.this, trackValues));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
