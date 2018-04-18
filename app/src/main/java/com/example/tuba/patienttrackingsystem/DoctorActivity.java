package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import at.markushi.ui.CircleButton;

public class DoctorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button editProfile;
    CardView appButton;
    CardView addpatientButton;
    CardView mypatientsButton;
    CardView emergencynoteButton;

    TextView doctor_name;
    TextView doctor_email;
    TextView doctor_address;
    TextView doctor_department;
    TextView doctor_name2;

    CircleButton btnMessages;
    CircleButton btnNotification;
    CircleButton btnQrCodeScanner;

    final Activity activity=this;
    String AES = "AES";
    static String result2;
    String password="123";
    static boolean success = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
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

        editProfile = (Button) findViewById(R.id.bUploadImage);
        appButton = (CardView) findViewById(R.id.appointmentButton);
        addpatientButton = (CardView) findViewById(R.id.addPatientButton);
        mypatientsButton = (CardView) findViewById(R.id.mypatientsButton);
        emergencynoteButton = (CardView) findViewById(R.id.emergencyButton);
        doctor_name = (TextView) findViewById(R.id.text_doctorname);
        doctor_email = (TextView) findViewById(R.id.text_doctoremail);
        doctor_address = (TextView) findViewById(R.id.text_doctoraddress);
        doctor_department = (TextView) findViewById(R.id.text_doctordepartment);
        doctor_name2 = (TextView) findViewById(R.id.text_doctorname2);


        btnMessages=(CircleButton) findViewById(R.id.btn_Message);
        btnNotification=(CircleButton) findViewById(R.id.btn_Notification);
        btnQrCodeScanner=(CircleButton) findViewById(R.id.btn_QRScanner);

        btnQrCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();


            }
        });

        btnMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DoctorActivity.this, "Please select the person you want to send a message", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DoctorActivity.this, DoctorMyPatientsActivity.class);
                startActivity(intent);

            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultTrack = new Intent(DoctorActivity.this, DoctorShowTrackActivity.class);
                startActivity(resultTrack);
            }
        });


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDoctorEditProfile();
            }
        });

        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppointments();
            }

        });

        addpatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openaddPatient();
            }

        });

        mypatientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openmyPatients();
            }

        });

        emergencynoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openemergencyNotes();
            }

        });

        new DoctorActivity.GetDoctorDetails().execute("http://192.168.156.169/Service1.svc/DoctorDetails");
        //new DoctorActivity.GetDoctorDetails().execute("http://192.168.0.10/Service1.svc/DoctorDetails");



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
                Intent loginIntent = new Intent(DoctorActivity.this, LoginActivity.class);
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
        getMenuInflater().inflate(R.menu.doctor, menu);
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
        int id = item.getItemId();

        if (id == R.id.nav_doctor_profile) {
            Intent homeIntent = new Intent(DoctorActivity.this, DoctorActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_doctor_qrScanner) {
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        } else if (id == R.id.nav_doctor_appointment) {
            Intent appIntent = new Intent(DoctorActivity.this, DoctorAppointmentsActivity.class);
            startActivity(appIntent);

        } else if (id == R.id.nav_doctor_setting) {
            Intent settingIntent = new Intent(DoctorActivity.this, DoctorSettingsActivity.class);
            startActivity(settingIntent);

        } else if (id == R.id.nav_doctor_logOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent loginIntent = new Intent(DoctorActivity.this, LoginActivity.class);
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

        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.doctor_mainLayout, fragment, fragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openAppointments() {
        final Intent appIntent = new Intent(this, DoctorAppointmentsActivity.class);
        startActivity(appIntent);
    }

    public void openaddPatient() {
        final Intent addpatientIntent = new Intent(this, DoctorAddPatientActivity.class);
        startActivity(addpatientIntent);
    }

    public void openmyPatients() {
        final Intent mypatientsIntent = new Intent(this, DoctorMyPatientsActivity.class);
        startActivity(mypatientsIntent);
    }

    public void openemergencyNotes() {
        final Intent emergencynoteIntent = new Intent(this, DoctorEmergencyNotesActivity.class);
        startActivity(emergencynoteIntent);
    }

    public void openDoctorEditProfile(){
        final Intent doctoreditprofileIntent = new Intent(this, DoctorEditProfileActivity.class);
        startActivity(doctoreditprofileIntent);
    }


    class GetDoctorDetails extends AsyncTask<String, Void, String> {
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
                        String name = jsonArray.getJSONObject(i).getString("DoctorName");
                        String email = jsonArray.getJSONObject(i).getString("Email");
                        String address = jsonArray.getJSONObject(i).getString("Address");
                        String department = jsonArray.getJSONObject(i).getString("DepartmentName");
                        String doctorTc= jsonArray.getJSONObject(i).getString("DoctorTc");

                        if(LoginActivity.girenDoctor.equals(doctorTc)){

                            doctor_name.setText(name);
                            doctor_email.setText(email);
                            doctor_address.setText(address);
                            doctor_department.setText(department);
                            doctor_name2.setText(name);
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        try {
            result2 = decrypt(result,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result2 != null) {
            if (result2 == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, result2, Toast.LENGTH_LONG).show();
                new GetMyPatients().execute("http://192.168.156.169/Service1.svc/MyPatients");

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private String decrypt(IntentResult result, String password)throws Exception {
        SecretKeySpec key = generateKey(password);
        Cipher c =Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodedValue= Base64.decode(result.getContents(), Base64.DEFAULT);
        byte[] decVal=c.doFinal(decodedValue);
        String decryptedValue = new String(decVal);
        return decryptedValue;

    }

    private SecretKeySpec generateKey(String password)throws Exception {
        final MessageDigest digest =MessageDigest.getInstance("SHA-256");
        byte[] bytes  =password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


    class GetMyPatients extends AsyncTask<String, Void, String> {
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
                        String pName = jsonArray.getJSONObject(i).getString("Patient_Name");
                        String dTc = jsonArray.getJSONObject(i).getString("DoctorTc");
                        String ptTc = jsonArray.getJSONObject(i).getString("Patient_Tc");

                        if (LoginActivity.girenDoctor.equals(dTc)) {
                            if (ptTc.equals(result2)) {
                                Intent intent = new Intent(DoctorActivity.this, DoctorPatientAssayResultActivity.class);
                                startActivity(intent);
                                success=false;
                                break;
                            }
                            /*else if(!ptTc.equals(result2) &&  success == false){
                                 Toast.makeText(DoctorActivity.this, "Patient could not found in your patients.", Toast.LENGTH_LONG).show();
                            }*/
                        }
                    }
                    if( success == true){
                        Toast.makeText(DoctorActivity.this, "Patient could not found in your patients.", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}









