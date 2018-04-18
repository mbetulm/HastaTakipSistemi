package com.example.tuba.patienttrackingsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.List;

public class DoctorShowTrackActivity extends AppCompatActivity  {

    ListView relationreport_list_view;
    CustomDrRelationReportListAdapter customDrRelationReportListAdapter;
    List<DoctorGetRelationReport> relationList = new ArrayList<>();
    String baseurl = "http://192.168.156.169/Service1.svc/GetDoctorRelationReport";
    String date;
    String diseaseName;
    public static String showTrack_selectedPatient;
    public static String showTrack_selectedPatientTc;
    public static String showTrack_selectedDisease_ID;
    public static String showTrack_selectedEnd_Track;
    public static String showTrack_selectedStart_Track;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_show_track);

        relationreport_list_view = (ListView) findViewById(R.id.trackListView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        date = (sdf2.format(calendar.getTime()));

        new GetDoctorRelationReport().execute(baseurl + "/" + LoginActivity.girenDoctor);


        relationreport_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                DoctorGetRelationReport relationReport = (DoctorGetRelationReport) adapter.getItemAtPosition(position);
                showTrack_selectedPatient = relationReport.patient_name;
                showTrack_selectedPatientTc = relationReport.patient_tc;
                showTrack_selectedEnd_Track = relationReport.systemDate;
                showTrack_selectedStart_Track = relationReport.start_track;

                if (relationReport.disease_name.contains("Blood Pressure")) {
                    showTrack_selectedDisease_ID = "1";
                    Intent i = new Intent(DoctorShowTrackActivity.this, DoctorGetPatientFullTrackReport.class);
                    startActivity(i);

                } else if (relationReport.disease_name.contains("Blood Sugar")) {
                    showTrack_selectedDisease_ID = "2";
                    Intent i = new Intent(DoctorShowTrackActivity.this, DoctorGetPatientFullTrackReport.class);
                    startActivity(i);

                } else if (relationReport.disease_name.contains("Heart Rate")) {
                    showTrack_selectedDisease_ID = "3";
                    Intent i = new Intent(DoctorShowTrackActivity.this, DoctorGetPatientFullTrackReport.class);
                    startActivity(i);

                } else if (relationReport.disease_name.contains("Weight")) {
                    showTrack_selectedDisease_ID = "4";
                    Intent i = new Intent(DoctorShowTrackActivity.this, DoctorGetPatientTrackReport.class);
                    startActivity(i);

                } else if (relationReport.disease_name.contains("Allergy")) {
                    showTrack_selectedDisease_ID = "5";
                    Intent i = new Intent(DoctorShowTrackActivity.this, DoctorGetPatientTrackReport.class);
                    startActivity(i);

                }


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    class GetDoctorRelationReport extends AsyncTask<String, Void, String> {
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
                        String startTrack = jsonArray.getJSONObject(i).getString("Start_Track");
                        String ptTc = jsonArray.getJSONObject(i).getString("Patient_Tc");
                        String ptName = jsonArray.getJSONObject(i).getString("PatientName");
                        String diseaseId = jsonArray.getJSONObject(i).getString("Disease_ID");

                        if (diseaseId.contains("1")) {
                            diseaseName = "Blood Pressure";
                        } else if (diseaseId.contains("2")) {
                            diseaseName = "Blood Sugar";
                        } else if (diseaseId.contains("3")) {
                            diseaseName = "Heart Rate";
                        } else if (diseaseId.contains("4")) {
                            diseaseName = "Weight";
                        } else if (diseaseId.contains("5")) {
                            diseaseName = "Allergy";
                        }

                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        if (format.parse(endTrack).before(format.parse(date))) {
                            relationList.add(new DoctorGetRelationReport(endTrack, ptTc, ptName, diseaseName, startTrack));
                        }
                    }
                    customDrRelationReportListAdapter = new CustomDrRelationReportListAdapter(getApplicationContext(), relationList);
                    relationreport_list_view.setAdapter(customDrRelationReportListAdapter);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

