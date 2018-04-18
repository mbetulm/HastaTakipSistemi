package com.example.tuba.patienttrackingsystem;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class DoctorGetPatientFullTrackReport extends AppCompatActivity {

    String [] trackValuesHeaders={"Date","Morning","Afternoon","Evening"};
    public static String[][] trackValues;
    ArrayList<TrackValues> trackValuesList = new ArrayList<>();
    String baseurl1 = "http://192.168.156.169/Service1.svc/DoctorGetTrackBloodPressureReport";
    String baseurl2 = "http://192.168.156.169/Service1.svc/DoctorGetTrackBloodSugarReport";
    String baseurl3 = "http://192.168.156.169/Service1.svc/DoctorGetTrackHeartRateReport";
    String system_date;
    static TableView<String[]> tb;
    TextView trackPatientName;
    Button fullGraphButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_get_patient_full_track_report);

        tb = (TableView<String[]>) findViewById(R.id.tableView);
        tb.setColumnCount(4);
        tb.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
        TableColumnDpWidthModel columnModel = new TableColumnDpWidthModel(DoctorGetPatientFullTrackReport.this, 4, 120);
        columnModel.setColumnWidth(1, 100);
        columnModel.setColumnWidth(2, 100);
        columnModel.setColumnWidth(3, 100);
        tb.setColumnModel(columnModel);

        trackPatientName = (TextView)findViewById(R.id.patientTrackName);
        trackPatientName.setText(DoctorShowTrackActivity.showTrack_selectedPatient);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        system_date = (sdf2.format(calendar.getTime()));

        if (DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("1")){
            new GetTrackBloodPressureReport().execute(baseurl1 + "/" + DoctorShowTrackActivity.showTrack_selectedPatientTc);

        }else if(DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("2")){
            new GetTrackBloodSugarReport().execute(baseurl2 + "/" + DoctorShowTrackActivity.showTrack_selectedPatientTc);

        }else if (DoctorShowTrackActivity.showTrack_selectedDisease_ID.equals("3")){
            new GetTrackHeartRateReport().execute(baseurl3 + "/" + DoctorShowTrackActivity.showTrack_selectedPatientTc);

        }

        fullGraphButton = (Button)findViewById(R.id.full_graphBtn);
        fullGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorGetPatientFullTrackReport.this, DoctorShowGraphActivity.class);
                startActivity(intent);
            }
        });

    }

    class GetTrackBloodPressureReport extends AsyncTask<String, Void, String> {
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
                        String morning_value = jsonArray.getJSONObject(i).getString("Morning_value");
                        String afternoon_value = jsonArray.getJSONObject(i).getString("Afternoon_value");
                        String evening_value = jsonArray.getJSONObject(i).getString("Evening_value");

                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        if ( /*format.parse(DoctorShowTrackActivity.showTrack_selectedStart_Track).after(format.parse(date)) && format.parse(endTrack).before(format.parse(date))*/true) {
                            TrackValues trackValues=new TrackValues();

                            trackValues.setDate(date);
                            trackValues.setMorning(morning_value);
                            trackValues.setAfternoon(afternoon_value);
                            trackValues.setEvening(evening_value);
                            trackValuesList.add(trackValues);
                        }
                    }

                    trackValues = new String[trackValuesList.size()][4];
                    for (int i=0;i<trackValuesList.size();i++) {
                        TrackValues s = trackValuesList.get(i);
                        trackValues[i][0]=s.getDate();
                        trackValues[i][1]=s.getMorning();
                        trackValues[i][2]=s.getAfternoon();
                        trackValues[i][3]=s.getEvening();

                    }
                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(DoctorGetPatientFullTrackReport.this, trackValuesHeaders));
                    tb.setDataAdapter(new SimpleTableDataAdapter(DoctorGetPatientFullTrackReport.this, trackValues));


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class GetTrackBloodSugarReport extends AsyncTask<String, Void, String> {
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
                        String morning_value = jsonArray.getJSONObject(i).getString("Morning_value");
                        String afternoon_value = jsonArray.getJSONObject(i).getString("Afternoon_value");
                        String evening_value = jsonArray.getJSONObject(i).getString("Evening_value");

                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        if ( /*format.parse(DoctorShowTrackActivity.showTrack_selectedStart_Track).after(format.parse(date)) && format.parse(endTrack).before(format.parse(date))*/true) {
                            TrackValues trackValues = new TrackValues();

                            trackValues.setDate(date);
                            trackValues.setMorning(morning_value);
                            trackValues.setAfternoon(afternoon_value);
                            trackValues.setEvening(evening_value);
                            trackValuesList.add(trackValues);
                        }
                    }

                    trackValues = new String[trackValuesList.size()][4];
                    for (int i = 0; i < trackValuesList.size(); i++) {
                        TrackValues s = trackValuesList.get(i);
                        trackValues[i][0] = s.getDate();
                        trackValues[i][1] = s.getMorning();
                        trackValues[i][2] = s.getAfternoon();
                        trackValues[i][3] = s.getEvening();
                    }
                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(DoctorGetPatientFullTrackReport.this, trackValuesHeaders));
                    tb.setDataAdapter(new SimpleTableDataAdapter(DoctorGetPatientFullTrackReport.this, trackValues));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class GetTrackHeartRateReport extends AsyncTask<String, Void, String> {
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
                        String morning_value = jsonArray.getJSONObject(i).getString("Morning_value");
                        String afternoon_value = jsonArray.getJSONObject(i).getString("Afternoon_value");
                        String evening_value = jsonArray.getJSONObject(i).getString("Evening_value");

                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        if ( /*format.parse(DoctorShowTrackActivity.showTrack_selectedStart_Track).after(format.parse(date)) && format.parse(endTrack).before(format.parse(date))*/true) {
                            TrackValues trackValues = new TrackValues();

                            trackValues.setDate(date);
                            trackValues.setMorning(morning_value);
                            trackValues.setAfternoon(afternoon_value);
                            trackValues.setEvening(evening_value);
                            trackValuesList.add(trackValues);
                        }
                    }

                    trackValues = new String[trackValuesList.size()][4];
                    for (int i = 0; i < trackValuesList.size(); i++) {
                        TrackValues s = trackValuesList.get(i);
                        trackValues[i][0] = s.getDate();
                        trackValues[i][1] = s.getMorning();
                        trackValues[i][2] = s.getAfternoon();
                        trackValues[i][3] = s.getEvening();
                    }
                    tb.setHeaderAdapter(new SimpleTableHeaderAdapter(DoctorGetPatientFullTrackReport.this, trackValuesHeaders));
                    tb.setDataAdapter(new SimpleTableDataAdapter(DoctorGetPatientFullTrackReport.this, trackValues));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
