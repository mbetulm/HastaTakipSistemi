package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;
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

public class PatientWeightTrackActivity extends AppCompatActivity {

    public static String time;
    String date;
    Button addbutton;
    int num, dec;
    String kilo;
    TimePicker timePicker;

    NumberPicker pickerNum;
    NumberPicker pickerDec;
    private static final int Disease_ID = 4;
    private static final int MAX_NUMBER_VALUE = 299;
    private static final int MIN_NUMBER_VALUE = 1;
    private static final int NUMBER_VALUE = 100;
    private static final int MAX_DECIMAL_VALUE = 9;
    private static final int MIN_DECIMAL_VALUE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_weight_track);

        addbutton = (Button) findViewById(R.id.btn_addWeight);
        pickerNum = (NumberPicker) findViewById(R.id.numberPickerWeigth1);
        pickerDec = (NumberPicker) findViewById(R.id.numberPickerWeigth2);

        pickerNum.setMaxValue(MAX_NUMBER_VALUE);
        pickerNum.setMinValue(MIN_NUMBER_VALUE);
        pickerNum.setValue(NUMBER_VALUE);

        pickerDec.setMaxValue(MAX_DECIMAL_VALUE);
        pickerDec.setMinValue(MIN_DECIMAL_VALUE);
        pickerDec.setValue(MIN_DECIMAL_VALUE);

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);

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
                if (600 < get_time && get_time < 2400) {
                    num = pickerNum.getValue();
                    dec = pickerDec.getValue();
                    kilo = (num + "." + dec);
                    new PatientWeightTrack().execute("http://192.168.156.169/Service1.svc/PatientWeightTrack");

                }
            }
        });
    }

    class PatientWeightTrack extends AsyncTask<String, Void, String> {
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
                j.put("Weight_value", kilo);
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
                Toast.makeText(PatientWeightTrackActivity.this, "disease relation successfully ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PatientWeightTrackActivity.this, "disease relation does not successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
