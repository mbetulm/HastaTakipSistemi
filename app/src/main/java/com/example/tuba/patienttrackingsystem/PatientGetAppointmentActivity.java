package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class PatientGetAppointmentActivity extends AppCompatActivity implements View.OnClickListener{

    String base_url="http://192.168.156.169/Service1.svc/MyDoctors";
    Button btnDatePicker, btnTimePicker, confirmAppointment, checkAppointment;
    Spinner myDoctors;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ArrayList<String> doctors = new ArrayList<String>();
    ArrayList<Appointment> doctorsAppointment = new ArrayList<Appointment>();
    public ArrayAdapter mydoctorsAdapter;
    private final static int TIME_PICKER_INTERVAL = 10;
    String selectedDoctorTc, selectedDoctorName, selectedDeptName, selectedAppTime, selectedAppDate;
    boolean checkTrue= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_get_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmAppointment.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkTrue=true;
            }
        });
        btnTimePicker= (Button)findViewById(R.id.btn_time) ;
        txtTime = (EditText)findViewById(R.id.in_time);
        txtTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                confirmAppointment.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkTrue=true;
            }
        });

        confirmAppointment = (Button)findViewById(R.id.btn_confirmAppointment);
        confirmAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkTrue==true){
                    new PatientGetAppointmentActivity.ConfirmAppointment().execute("http://192.168.156.169/Service1.svc/ConfirmAppointment");
                }

            }
        });

        checkAppointment = (Button)findViewById(R.id.btn_checkAppointment);
        checkAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PatientGetAppointmentActivity.CheckAppointment().execute("http://192.168.156.169/Service1.svc/CheckAppointment");

            }
        });

        myDoctors = (Spinner)findViewById(R.id.spinner_mydoctors);
        new PatientGetAppointmentActivity.GetMyDoctors(PatientGetAppointmentActivity.this).execute(base_url);
        myDoctors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String doctorInfo= myDoctors.getSelectedItem().toString();
                String[] parts= doctorInfo.split("-");
                String name = parts[1];
                String department = parts[0];
                for (int t=0; t<doctorsAppointment.size();t++){
                    if(doctorsAppointment.get(t).doctorName.equals(name) && doctorsAppointment.get(t).departmentName.equals(department)){
                        selectedDoctorTc = doctorsAppointment.get(t).doctorTc;
                        selectedDoctorName = doctorsAppointment.get(t).doctorName;
                        selectedDeptName = doctorsAppointment.get(t).departmentName;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            selectedAppDate =txtDate.getText().toString();


                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
            datePickerDialog.show();
        }

        if (v == btnTimePicker) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            CustomTimePickerDialog customTimePickerDialog = new CustomTimePickerDialog(this,
                    new CustomTimePickerDialog.OnTimeSetListener(){
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            txtTime.setText(hourOfDay + ":" + minute);
                            selectedAppTime=txtTime.getText().toString();
                        }

                    }, mHour, mMinute / TIME_PICKER_INTERVAL, true);


            customTimePickerDialog.show();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    class GetMyDoctors extends AsyncTask<String, Void, String> {
        String status=null;
        Activity context;


        public GetMyDoctors (Activity context){
            this.context = context;
        }

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
                        Appointment a = new Appointment();
                        a.doctorName = jsonArray.getJSONObject(i).getString("DoctorName");
                        a.departmentName = jsonArray.getJSONObject(i).getString("DepartmentName");
                        a.patientTc = jsonArray.getJSONObject(i).getString("Patient_Tc");
                        a.doctorTc = jsonArray.getJSONObject(i).getString("DoctorTc");

                        if(LoginActivity.girenPatient.equals(a.patientTc)){
                            doctorsAppointment.add(a);
                            doctors.add(a.departmentName+"-"+a.doctorName);
                        }

                    }

                    mydoctorsAdapter = new ArrayAdapter(PatientGetAppointmentActivity.this, android.R.layout.simple_list_item_1, doctors );
                    myDoctors.setAdapter(mydoctorsAdapter);



                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    class ConfirmAppointment extends AsyncTask<String, Void, String> {
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
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);
                conn.addRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");

                JSONObject j = new JSONObject();
                j.put("Patient_Tc",LoginActivity.girenPatient);
                j.put("DoctorTc", selectedDoctorTc);
                j.put("DoctorName", selectedDoctorName );
                j.put("DepartmentName", selectedDeptName);
                j.put("Appointment_Date", selectedAppDate);
                j.put("Appointment_Time", selectedAppTime);


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
                Toast.makeText(PatientGetAppointmentActivity.this, "Appoinment confirm successfully ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(PatientGetAppointmentActivity.this, "Appoinment  does not confirm successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }

    class CheckAppointment extends AsyncTask<String, Void, String> {
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
                        String dTc= jsonArray.getJSONObject(i).getString("DoctorTc");
                        String ptTc = jsonArray.getJSONObject(i).getString("Patient_Tc");
                        String time=jsonArray.getJSONObject(i).getString("Appointment_Time");


                        if(selectedAppDate.equals(date) && selectedAppTime.equals(time) && selectedDoctorTc.equals(dTc)){
                            confirmAppointment.setEnabled(false);
                            Toast.makeText(PatientGetAppointmentActivity.this, "UnAvailable Appoinment",Toast.LENGTH_SHORT).show();
                            x =false;
                            checkTrue=false;
                            break;

                        }

                    }
                    if(x==true && checkTrue==true){
                        Toast.makeText(PatientGetAppointmentActivity.this, "Available Appoinment",Toast.LENGTH_SHORT).show();
                        confirmAppointment.setEnabled(true);

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

class Appointment{
    String doctorName;
    String doctorTc;
    String patientTc;
    String departmentName;

}



