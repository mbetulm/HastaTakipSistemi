package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

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

public class PatientBloodSugarTrackActivity extends AppCompatActivity {

    public static String time;
    String baseURL = "http://192.168.156.169/Service1.svc/PatientAfternoonBloodSugarTrack";
    String baseURL2 = "http://192.168.156.169/Service1.svc/PatientEveningBloodSugarTrack";
    String date;
    Button addbutton;
    int aclık_seker, tokluk_seker;
    String seker, ogle_seker, aksam_seker;

    NumberPicker Aclık;
    NumberPicker Tokluk;
    private static final int Disease_ID = 2;
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
        setContentView(R.layout.activity_patient_blood_sugar_track);

        addbutton = (Button) findViewById(R.id.btn_addBloodSugar);
        Aclık = (NumberPicker) findViewById(R.id.numberPickerAclık);
        Tokluk = (NumberPicker) findViewById(R.id.numberPickerTokluk);

        Aclık.setMaxValue(MAX_BP_VALUE);
        Aclık.setMinValue(MIN_BP_VALUE);
        Aclık.setValue(BP_SYS_VALUE);

        Tokluk.setMaxValue(MAX_BP_VALUE);
        Tokluk.setMinValue(MIN_BP_VALUE);
        Tokluk.setValue(BP_DIA_VALUE);

        Calendar takvim = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        time = (sdf.format(takvim.getTime()));
        final String h = time.substring(0, 2);
        String m = time.substring(3, 5);
        final int get_time = Integer.parseInt(h.concat(m));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        date = (sdf2.format(calendar.getTime()));


        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (600 < get_time && get_time < 1300) {
                    tokluk_seker = Tokluk.getValue();
                    aclık_seker = Aclık.getValue();
                    seker = (aclık_seker + "-" + tokluk_seker);
                    new PatientBloodSugarTrackActivity.PatientBloodSugarTrack().execute("http://192.168.156.169/Service1.svc/PatientBloodSugarTrack");

                } else if (1300 < get_time && get_time < 1800) {
                    tokluk_seker = Tokluk.getValue();
                    aclık_seker = Aclık.getValue();
                    ogle_seker = (aclık_seker + "-" + tokluk_seker);
                    new PatientBloodSugarTrackActivity.PatientAfternoonBloodSugarTrack().execute(baseURL + "/" + LoginActivity.girenPatient + "/" +
                            ogle_seker + "/" + Disease_ID + "/" + date );


                } else if (1800 < get_time && get_time < 2400) {
                    tokluk_seker = Tokluk.getValue();
                    aclık_seker = Aclık.getValue();
                    aksam_seker = (aclık_seker + "-" + tokluk_seker);
                    new PatientBloodSugarTrackActivity.PatientEveningBloodSugarTrack().execute(baseURL2 + "/" + LoginActivity.girenPatient + "/" +
                            aksam_seker + "/" + Disease_ID + "/" + date );
                }
            }
        });
    }

    class PatientBloodSugarTrack extends AsyncTask<String, Void, String> {
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
                j.put("Morning_value", seker);
                j.put("Afternoon_value", 0);
                j.put("Evening_value", 0);
                j.put("DiseaseID", Disease_ID);
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
                Toast.makeText(PatientBloodSugarTrackActivity.this, "disease relation successfully ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PatientBloodSugarTrackActivity.this, "disease relation does not successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }
    class PatientAfternoonBloodSugarTrack extends AsyncTask<String, Void, String> {
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
                j.put("Afternoon_value", ogle_seker);
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
                Toast.makeText(PatientBloodSugarTrackActivity.this, "Patient updated successfully ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PatientBloodSugarTrackActivity.this, "Patient does not update successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }

    class PatientEveningBloodSugarTrack extends AsyncTask<String, Void, String> {
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
                j.put("Evening_value",aksam_seker);
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
                Toast.makeText(PatientBloodSugarTrackActivity.this, "Patient updated successfully ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(PatientBloodSugarTrackActivity.this, "Patient does not update successfully ", Toast.LENGTH_LONG).show();
            }
        }
}

}
