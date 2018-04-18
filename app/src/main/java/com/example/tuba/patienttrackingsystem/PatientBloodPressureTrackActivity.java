package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PatientBloodPressureTrackActivity extends AppCompatActivity {

    public static String time;
    String baseURL = "http://192.168.156.169/Service1.svc/PatientAfternoonBloodPressureTrack";
    String baseURL2 = "http://192.168.156.169/Service1.svc/PatientEveningBloodPressureTrack";
    String date;
    Button addButton;
    NumberPicker Systole;
    NumberPicker Diastole;
    int kucuk_tansiyon, buyuk_tansiyon ;
    String tansiyon, öglenTansiyon,AksamTansiyon;
    private static final int Disease_ID = 1;
    private static final int MAX_NUMBER_VALUE = 299;
    private static final int MIN_NUMBER_VALUE = 1;
    private static final int NUMBER_VALUE = 100;
    private static final int MAX_DECIMAL_VALUE = 9;
    private static final int MIN_DECIMAL_VALUE = 0;
    private static final int DECIMAL_VALUE = 0;
    private static final int MAX_BP_VALUE = 499;
    private static final int MIN_BP_VALUE = 0;
    private static final int BP_SYS_VALUE = 128;
    private static final int BP_DIA_VALUE = 72;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_blood_pressure_track);

        addButton = (Button)findViewById(R.id.btn_addBloodPressure);
        Systole = (NumberPicker)findViewById(R.id.numberPickerSystole);
        Diastole = (NumberPicker)findViewById(R.id.numberPickerDiastole);

        Systole.setMaxValue(MAX_BP_VALUE);
        Systole.setMinValue(MIN_BP_VALUE);
        Systole.setValue(BP_SYS_VALUE);

        Diastole.setMaxValue(MAX_BP_VALUE);
        Diastole.setMinValue(MIN_BP_VALUE);
        Diastole.setValue(BP_DIA_VALUE);


        Calendar takvim = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        time = (sdf.format(takvim.getTime()));
        final String h = time.substring(0, 2);
        String m = time.substring(3, 5);
        final int get_time = Integer.parseInt(h.concat(m));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        date = (sdf2.format(calendar.getTime()));



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (600 < get_time && get_time < 1300){
                    kucuk_tansiyon = Diastole.getValue();
                    buyuk_tansiyon = Systole.getValue();
                    tansiyon = (buyuk_tansiyon + "-" + kucuk_tansiyon);
                    new PatientBloodPressureTrack().execute("http://192.168.156.169/Service1.svc/PatientBloodPressureTrack");

                }else if (1300 < get_time && get_time < 1800){
                    kucuk_tansiyon = Diastole.getValue();
                    buyuk_tansiyon = Systole.getValue();
                    öglenTansiyon = (buyuk_tansiyon + "-" + kucuk_tansiyon);
                    new PatientAfternoonBloodPressureTrack().execute(baseURL + "/" + LoginActivity.girenPatient + "/" +
                            öglenTansiyon + "/" + Disease_ID + "/" + date );


                }else if (1800 < get_time && get_time < 2400){
                    kucuk_tansiyon = Diastole.getValue();
                    buyuk_tansiyon = Systole.getValue();
                    AksamTansiyon = (buyuk_tansiyon + "-" + kucuk_tansiyon);
                    new PatientEveningBloodPressureTrack().execute(baseURL2 + "/" + LoginActivity.girenPatient + "/" +
                            AksamTansiyon + "/" + Disease_ID + "/" + date );
                }
            }
        });
    }

    class PatientBloodPressureTrack extends AsyncTask<String, Void, String> {
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
                j.put("Patient_Tc", LoginActivity.girenPatient);
                j.put("Morning_value", tansiyon);
                j.put("Afternoon_value", 0);
                j.put("Evening_value", 0);
                j.put("DiseaseID", Disease_ID );
                j.put("Date", date);
                j.put("Time", time);


                OutputStream out = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
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
                Toast.makeText(PatientBloodPressureTrackActivity.this, "disease relation successfully ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PatientBloodPressureTrackActivity.this, "disease relation does not successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }

    class PatientAfternoonBloodPressureTrack extends AsyncTask<String, Void, String> {
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
                j.put("Patient_Tc", LoginActivity.girenPatient);
                j.put("Afternoon_value", öglenTansiyon);
                j.put("DiseaseID", Disease_ID);
                j.put("Date", date);

                OutputStream out = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
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
                Toast.makeText(PatientBloodPressureTrackActivity.this, "Patient updated successfully ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PatientBloodPressureTrackActivity.this, "Patient does not update successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }

        class PatientEveningBloodPressureTrack extends AsyncTask<String, Void, String> {
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
                    j.put("Patient_Tc",LoginActivity.girenPatient);
                    j.put("Evening_value",AksamTansiyon);
                    j.put("DiseaseID",Disease_ID);
                    j.put("Date",date);

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
                    Toast.makeText(PatientBloodPressureTrackActivity.this, "Patient updated successfully ", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(PatientBloodPressureTrackActivity.this, "Patient does not update successfully ", Toast.LENGTH_LONG).show();
                }
            }
    }
}
