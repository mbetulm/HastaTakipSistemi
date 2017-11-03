package com.example.tuba.patienttrackingsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class DoctorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button editProfile;
    CardView appButton;
    CardView addpatientButton;
    CardView mypatientsButton;
    CardView emergencynoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editProfile= (Button) findViewById(R.id.bUploadImage);
        appButton = (CardView) findViewById(R.id.appointmentButton);
        addpatientButton = (CardView) findViewById(R.id.addPatientButton);
        mypatientsButton = (CardView) findViewById(R.id.mypatientsButton);
        emergencynoteButton = (CardView) findViewById(R.id.emergencyButton);

        editProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorActivity.this);
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

        appButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openAppointments();
            }

        });

        addpatientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openaddPatient();
            }

        });

        mypatientsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openmyPatients();
            }

        });

        emergencynoteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openemergencyNotes();
            }

        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          /*  if (fragment instanceof DoctorGraphicFragment){
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
        getMenuInflater().inflate(R.menu.doctor, menu);
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
    /*
    private void showHome(){
        fragment = new DoctorGraphicFragment();
        if (fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.doctor_mainLayout, fragment, fragment.getTag()).commit();
        }
    }*/

    Fragment fragment = null;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_doctor_profile) {
            Intent homeIntent = new Intent(DoctorActivity.this, DoctorActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_doctor_qrScanner) {
            Intent qrIntent = new Intent(DoctorActivity.this, DoctorQrActivity.class);
            startActivity(qrIntent);
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
                    // finish(); // uygulamadan tamamen çıkar
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

        if (fragment != null){
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.doctor_mainLayout, fragment, fragment.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openAppointments(){
        final Intent appIntent = new Intent(this, DoctorAppointmentsActivity.class);
        startActivity(appIntent);
    }

    public void openaddPatient(){
        final Intent addpatientIntent = new Intent(this, DoctorAddPatientActivity.class);
        startActivity(addpatientIntent);
    }

    public void openmyPatients(){
        final Intent mypatientsIntent = new Intent(this, DoctorMyPatientsActivity.class);
        startActivity(mypatientsIntent);
    }

    public void openemergencyNotes(){
        final Intent emergencynoteIntent = new Intent(this, DoctorEmergencyNotesActivity.class);
        startActivity(emergencynoteIntent);
    }


}
