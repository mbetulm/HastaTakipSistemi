package com.example.tuba.patienttrackingsystem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DoctorAppointmentsActivity extends AppCompatActivity {

    ListView appointment_list_view;
    CustomDrListAdapter customListAdapter;
    List<DoctorMyAppointment> appList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        appointment_list_view = (ListView) findViewById(R.id.doctor_appointment_list);

        new GetDoctorAppointments().execute("http://192.168.156.169/Service1.svc/CheckAppointment");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    class GetDoctorAppointments extends AsyncTask<String, Void, String> {
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
                        String ptName = jsonArray.getJSONObject(i).getString("PatientName");

                        if (LoginActivity.girenDoctor.equals(dTc)){
                            appList.add(new DoctorMyAppointment(ptName,ptTc,date,time));
                        }
                    }
                    customListAdapter = new CustomDrListAdapter(getApplicationContext(), appList);
                    appointment_list_view.setAdapter(customListAdapter);



                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}



