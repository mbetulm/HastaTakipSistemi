package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

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
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import at.markushi.ui.CircleButton;

public class PatientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button editProfile;
    CardView diseaseButton, pillButton,allergyButton,p_appointmentButton, assayButton,p_emergencyNoteButton;
    CircleButton btnMessages;
    CircleButton btnNotification;
    CircleButton btnQrCode;
    Dialog mydialog;
    TextView patientName, patientEmail, patientAddress;
    ImageView qr;
    String password="123";
    String text2Qr;
    String AES="AES";
    String base_url="http://192.168.156.169/Service1.svc/PatientDisease";
    //String base_url="http://192.168.0.10/Service1.svc/PatientDisease";

    public  static boolean isBloodPressure=false;
    public static boolean isBloodSugar=false;
    public static boolean isHeartRate=false;
    public static boolean isWeight=false;
    public static boolean isAllergy=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mydialog=new Dialog(this);
        btnMessages=(CircleButton) findViewById(R.id.btn_Message);
        btnNotification=(CircleButton) findViewById(R.id.btn_Notification);
        btnQrCode=(CircleButton) findViewById(R.id.btn_QR);

        btnQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowPopup(view);
            }
        });

        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri= Uri.parse("sms:(0507 264-8495)");
                Intent intent= new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() { // step counter
            @Override
            public void onClick(View view) {
                Intent graphcIntent = new Intent(PatientActivity.this, PatientGraphicActivity.class);
                startActivity(graphcIntent);
            }
        });

        editProfile= (Button) findViewById(R.id.bUploadImage);
        diseaseButton = (CardView)findViewById(R.id.diseaseButton);
        pillButton = (CardView)findViewById(R.id.pillButton);
        allergyButton = (CardView)findViewById(R.id.allergyButton);
        p_appointmentButton = (CardView)findViewById(R.id.p_appointmentButton);
        assayButton = (CardView)findViewById(R.id.assayButton);
        p_emergencyNoteButton = (CardView)findViewById(R.id.p_emergencyNoteButton);
        patientName = (TextView)findViewById(R.id.txt_patient_name);
        patientEmail = (TextView)findViewById(R.id.txt_patient_email);
        patientAddress = (TextView)findViewById(R.id.txt_patient_address);

        editProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openPatientEditProfile();
            }

        });

        diseaseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_patient_disease();
            }

        });

        pillButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_patient_pill();
            }

        });

        allergyButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_patient_allery();
            }

        });

        p_appointmentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_patient_appointment();
            }

        });
        assayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_patient_assay();
            }

        });

        p_emergencyNoteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                open_patient_emergencyNote();
            }

        });

        new PatientActivity.GetPatientDetails().execute("http://192.168.156.169/Service1.svc/PatientDetails");
        //new PatientActivity.GetPatientDetails().execute("http://192.168.0.10/Service1.svc/PatientDetails");

        new PatientActivity.PatientDisease().execute(base_url+"/"+LoginActivity.girenPatient);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent loginIntent = new Intent(PatientActivity.this, LoginActivity.class);
                finishAffinity();
                startActivity(loginIntent);
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    Fragment fragment = null;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent homeIntent = new Intent(PatientActivity.this, PatientActivity.class);
            startActivity(homeIntent);

        } else if (id == R.id.nav_QR) {
            Intent qrIntent = new Intent(PatientActivity.this, PatientQRActivity.class);
            startActivity(qrIntent);

        } else if (id == R.id.nav_emergency) {

            Uri uri = Uri.parse("tel:112");
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(intent);

        }else if (id == R.id.nav_location){
            Intent locationIntent = new Intent(PatientActivity.this, PatientLocationActivity.class);
            startActivity(locationIntent);


        } else if (id == R.id.nav_setting) {
            Intent settingIntent = new Intent(PatientActivity.this, PatientSettingActivity.class);
            startActivity(settingIntent);

        } else if (id == R.id.nav_logOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //finish(); tamamen uygulamadan çıkar
                    Intent loginIntent = new Intent(PatientActivity.this, LoginActivity.class);
                    finishAffinity();
                    startActivity(loginIntent);
                }
            });

            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        if (fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.patient_mainLayout, fragment, fragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void open_patient_disease() {
        final Intent patientDiseaseIntent = new Intent(this, PatientDiseaseActivity.class);
        startActivity(patientDiseaseIntent);

    }

    public void open_patient_pill() {
        final Intent patientPillIntent = new Intent(this, PatientPillActivity.class);
        startActivity(patientPillIntent);

    }

    public void openPatientEditProfile(){
        final Intent patientEditProfile = new Intent(this, PatientEditProfileActivity.class);
        startActivity(patientEditProfile);
    }

    public void open_patient_allery() {
        final Intent patientAllergyIntent = new Intent(this, PatientSurveyActivity.class);
        startActivity(patientAllergyIntent);

    }

    public void open_patient_appointment() {
        final Intent patientAppointmnetIntent = new Intent(this, PatientAppointmentActivity.class);
        startActivity(patientAppointmnetIntent);

    }

    public void open_patient_assay() {
        final Intent patientAssayIntent = new Intent(this, PatientAssayActivity.class);
        startActivity(patientAssayIntent);

    }

    public void open_patient_emergencyNote() {
        final Intent patientEmergencyNoteIntent = new Intent(this, PatientEmergencyNoteActivity.class);
        startActivity(patientEmergencyNoteIntent);

    }

    public void ShowPopup(View v){
        TextView txtclose;
        Bitmap bitmap=null;
        try {
            text2Qr = encrypt(LoginActivity.girenPatient.toString().trim(), password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);

        }
        catch(WriterException e){
            e.printStackTrace();
        }

        mydialog = new Dialog(this);
        mydialog.setContentView(R.layout.custom_popup);
        ImageView image = (ImageView) mydialog.findViewById(R.id.qr_img);
        image.setImageBitmap(bitmap);

        txtclose= (TextView) mydialog.findViewById(R.id.txtclose);
        txtclose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mydialog.dismiss();

            }
        });

        mydialog.show();

    }






    class GetPatientDetails extends AsyncTask<String, Void, String> {
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
                        String name = jsonArray.getJSONObject(i).getString("Patient_Name");
                        String patientTc = jsonArray.getJSONObject(i).getString("Patient_Tc");
                        String address = jsonArray.getJSONObject(i).getString("Patient_Adress");
                        String email = jsonArray.getJSONObject(i).getString("Patient_Email");

                        if(LoginActivity.girenPatient.equals(patientTc)){

                            patientName.setText(name);
                            patientEmail.setText(email);
                            patientAddress.setText(address);

                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private String encrypt(String Data, String password) throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal,Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password)throws Exception {
        final MessageDigest digest =MessageDigest.getInstance("SHA-256");
        byte[] bytes  =password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


    class PatientDisease extends AsyncTask<String, Void, String> {
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
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String ptTc = jsonArray.getJSONObject(i).getString("Patient_Tc");
                        String startTrack = jsonArray.getJSONObject(i).getString("Start_Track").trim();
                        String endTrack = jsonArray.getJSONObject(i).getString("End_Track").trim();
                        String diseaseId = jsonArray.getJSONObject(i).getString("Disease_ID").trim();

                        if(diseaseId.contains("1")){
                            isBloodPressure=true;
                        }
                        if(diseaseId.contains("2")){
                            isBloodSugar=true;
                        }
                        if(diseaseId.contains("3")){
                            isHeartRate=true;
                        }
                        if(diseaseId.contains("4")){
                            isWeight=true;
                        }
                        if(diseaseId.contains("5")){
                            isAllergy=true;
                        }


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

