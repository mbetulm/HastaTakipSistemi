package com.example.tuba.patienttrackingsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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

public class PatientActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button editProfile;
    CardView diseaseButton, pillButton,allergyButton,p_appointmentButton, assayButton,p_emergencyNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        editProfile= (Button) findViewById(R.id.bUploadImage);

        diseaseButton = (CardView)findViewById(R.id.diseaseButton);
        pillButton = (CardView)findViewById(R.id.pillButton);
        allergyButton = (CardView)findViewById(R.id.allergyButton);
        p_appointmentButton = (CardView)findViewById(R.id.p_appointmentButton);
        assayButton = (CardView)findViewById(R.id.assayButton);
        p_emergencyNoteButton = (CardView)findViewById(R.id.p_emergencyNoteButton);




        editProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientActivity.this);
                builder.setMessage("Hello");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
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
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {/*
            if (fragment instanceof PatientGraphicFragment){ // hasta profil-home ekranıyle değiştir
                //super.onBackPressed(); close app
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to exit?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
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

            }else {
                showHome();
            }*/
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*private void showHome(){
        fragment = new PatientGraphicFragment();
        if (fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.patient_mainLayout, fragment, fragment.getTag()).commit();
        }
    }*/

    Fragment fragment = null;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_graphic) {

            Intent graphcIntent = new Intent(PatientActivity.this, PatientGraphicActivity.class);
            startActivity(graphcIntent);

        } else if (id == R.id.nav_profile) {
            Intent homeIntent = new Intent(PatientActivity.this, PatientActivity.class);
            startActivity(homeIntent);

        } else if (id == R.id.nav_QR) {
            Intent qrIntent = new Intent(PatientActivity.this, PatientQRActivity.class);
            startActivity(qrIntent);

        } else if (id == R.id.nav_emergency) {
            Intent emergencyIntent = new Intent(PatientActivity.this, PatientEmergncyActivity.class);
            startActivity(emergencyIntent);

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

    public void open_patient_allery() {
        final Intent patientAllergyIntent = new Intent(this, PatientAllergyActivity.class);
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



}
