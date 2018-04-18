package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class DoctorTrackActivity extends AppCompatActivity {

    TextView patientName, patientGender, patientDofB, patientHeight, patientWeight;
    public static boolean bloodPressure=false;
    public static boolean bloodSugar=false;
    public static boolean heartRate=false;
    public static boolean Weight=false;
    public static boolean Allergy=false;
    CheckBox blood_pressure, blood_sugar, heart_rate, weight, allergy;
    RadioGroup radioGroup;
    String duration="";
    String startTrack=null;
    String endTrack=null;
    Button confirm;
    public static int DISEASE_ID;

    String base_url="http://192.168.156.169/Service1.svc/TrackPatient";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_track);
        radioGroup = (RadioGroup)findViewById(R.id.durationRadioGroup);




        patientName = (TextView)findViewById(R.id.txt_selectedpatientName);
        patientGender = (TextView)findViewById(R.id.txt_selectedpatientGender);
        patientDofB = (TextView)findViewById(R.id.txt_selectedpatientDateOfBirth);
        patientHeight = (TextView)findViewById(R.id.txt_selectedpatientHeight);
        patientWeight = (TextView)findViewById(R.id.txt_selectedpatientWeight);
        blood_pressure = (CheckBox)findViewById(R.id.checkboxBloodPressure);
        blood_sugar = (CheckBox)findViewById(R.id.checkboxBloodSugar);
        heart_rate = (CheckBox)findViewById(R.id.checkboxHeartRate);
        weight = (CheckBox)findViewById(R.id.checkboxWeight);
        allergy = (CheckBox)findViewById(R.id.checkboxAllergy);
        confirm = (Button)findViewById(R.id.buttonSubmitData);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checked= radioGroup.getCheckedRadioButtonId();
                switch (checked){
                    case R.id.duration7: {duration="7"; break;}
                    case R.id.duration15: {duration="15"; break;}
                    case R.id.duration30: {duration="30"; break;}
                }
                startTrack = sistemTarihiniGetir("dd.MM.yyyy");
                endTrack = calculateEndDate(duration);



                if (blood_pressure.isChecked()){
                    bloodPressure=true;
                    DISEASE_ID=1;
                    new TrackPatient().execute(base_url+"/"+DoctorMyPatientsActivity.selected_TC+"/"+LoginActivity.girenDoctor+"/"+DISEASE_ID+"/"+duration+"/"+startTrack+"/"+endTrack);
                }



                if(blood_sugar.isChecked()){
                    bloodSugar=true;
                    DISEASE_ID=2;
                    new TrackPatient().execute(base_url+"/"+DoctorMyPatientsActivity.selected_TC+"/"+LoginActivity.girenDoctor+"/"+DISEASE_ID+"/"+duration+"/"+startTrack+"/"+endTrack);
                }



                if(heart_rate.isChecked()){
                    heartRate=true;
                    DISEASE_ID=3;
                    new TrackPatient().execute(base_url+"/"+DoctorMyPatientsActivity.selected_TC+"/"+LoginActivity.girenDoctor+"/"+DISEASE_ID+"/"+duration+"/"+startTrack+"/"+endTrack);

                }



                if(weight.isChecked()){
                    Weight=true;
                    DISEASE_ID=4;
                    new TrackPatient().execute(base_url+"/"+DoctorMyPatientsActivity.selected_TC+"/"+LoginActivity.girenDoctor+"/"+DISEASE_ID+"/"+duration+"/"+startTrack+"/"+endTrack);

                }



                if(allergy.isChecked()){
                    Allergy=true;
                    DISEASE_ID=5;
                    new TrackPatient().execute(base_url+"/"+DoctorMyPatientsActivity.selected_TC+"/"+LoginActivity.girenDoctor+"/"+DISEASE_ID+"/"+duration+"/"+startTrack+"/"+endTrack);

                }
            }
        });

        new GetMyPatients().execute("http://192.168.156.169/Service1.svc/MyPatients");

    }

    class GetMyPatients extends AsyncTask<String, Void, String> {
        String status=null;
        Activity context;

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
                        String pName = jsonArray.getJSONObject(i).getString("Patient_Name");
                        String ptTc = jsonArray.getJSONObject(i).getString("Patient_Tc");
                        String pGender = jsonArray.getJSONObject(i).getString("Patient_Gender").trim();
                        String pDOB = jsonArray.getJSONObject(i).getString("Patient_DateOfBirth").trim();
                        String pHeight = jsonArray.getJSONObject(i).getString("Patient_Height").trim();
                        String pWeight = jsonArray.getJSONObject(i).getString("Patient_Weight").trim();

                        if(ptTc.equals(DoctorMyPatientsActivity.selected_TC)){
                            patientName.setText(pName);
                            patientGender.setText(pGender);
                            patientDofB.setText(pDOB);
                            patientWeight.setText(pWeight + " kg");
                            patientHeight.setText(pHeight + " cm");
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class TrackPatient extends AsyncTask<String, Void, String> {
        String status = null;
        Activity context;


        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn;
            BufferedReader reader;


            try {
                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.addRequestProperty("Content-Type", "application/json");


                JSONObject j = new JSONObject();
                j.put("patientTC",DoctorMyPatientsActivity.selected_TC);
                j.put("doctorTC",LoginActivity.girenDoctor);
                j.put("diseaseID",DISEASE_ID);
                j.put("duration",duration);
                j.put("startTrack",startTrack);
                j.put("endTrack",endTrack);

                OutputStream out = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                writer.write(j.toString());
                writer.flush();
                writer.close();
                out.close();
                conn.connect();

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
                Toast.makeText(DoctorTrackActivity.this, "disease relation successfully ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(DoctorTrackActivity.this, "disease relation does not successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String sistemTarihiniGetir(String tarihFormati)
    {
        Calendar takvim = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(tarihFormati);
        return sdf.format(takvim.getTime());
    }

    public String calculateEndDate(String duration){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,Integer.parseInt(duration));
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormatter.format(c.getTime());
    }
}
