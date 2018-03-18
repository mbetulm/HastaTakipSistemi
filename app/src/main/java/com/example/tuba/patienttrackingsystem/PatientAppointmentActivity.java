package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.R.id.list;

public class PatientAppointmentActivity extends AppCompatActivity  {

    ListView appointment_list_view;
    CustomListAdapter customListAdapter;
    List<PatientMyAppointment> appList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientAppointmentActivity.this, PatientGetAppointmentActivity.class);
                startActivity(i);
            }
        });

        appointment_list_view = (ListView) findViewById(R.id.appointment_list);

        new GetMyAppointments().execute("http://192.168.156.169/Service1.svc/CheckAppointment");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }



    class GetMyAppointments extends AsyncTask<String, Void, String> {
            String status=null;

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
                boolean x=true;
                if (result != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String date = jsonArray.getJSONObject(i).getString("Appointment_Date");
                            String dTc = jsonArray.getJSONObject(i).getString("DoctorTc");
                            String ptTc = jsonArray.getJSONObject(i).getString("Patient_Tc");
                            String time = jsonArray.getJSONObject(i).getString("Appointment_Time");
                            String docName = jsonArray.getJSONObject(i).getString("DoctorName");
                            String depName = jsonArray.getJSONObject(i).getString("DepartmentName");

                            if (LoginActivity.girenPatient.equals(ptTc)){
                                appList.add(new PatientMyAppointment(depName,docName,date,time));
                            }
                        }
                        customListAdapter = new CustomListAdapter(getApplicationContext(), appList);
                        appointment_list_view.setAdapter(customListAdapter);



                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }



