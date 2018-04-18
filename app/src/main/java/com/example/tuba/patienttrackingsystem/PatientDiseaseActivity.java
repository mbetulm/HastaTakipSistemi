package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.solver.SolverVariable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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


public class PatientDiseaseActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    String base_url="http://192.168.156.169/Service1.svc/PatientDisease";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_disease);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        linearLayout = (LinearLayout)findViewById(R.id.buttonsLayout);
        if( PatientActivity.isBloodPressure){
            Button btn=new Button(PatientDiseaseActivity.this);
            btn.setText("BLOOD PRESSURE");
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bloodpressure, 0, 0, 0);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bloodPressureIntent = new Intent(PatientDiseaseActivity.this, PatientBloodPressureTrackActivity.class);
                    startActivity(bloodPressureIntent);
                }
            });




        }if( PatientActivity.isBloodSugar){
            Button btn=new Button(PatientDiseaseActivity.this);
            btn.setText("BLOOD SUGAR");
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sugarblood_level, 0, 0, 0);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bloodSugarIntent = new Intent(PatientDiseaseActivity.this, PatientBloodSugarTrackActivity.class);
                    startActivity(bloodSugarIntent);
                }
            });


        }if(PatientActivity. isHeartRate){
            Button btn=new Button(PatientDiseaseActivity.this);
            btn.setText("HEART RATE");
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.heart_rate, 0, 0, 0);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bloodSugarIntent = new Intent(PatientDiseaseActivity.this, PatientHeartRateTrackActivity.class);
                    startActivity(bloodSugarIntent);
                }
            });

        }if( PatientActivity.isWeight){
            Button btn=new Button(PatientDiseaseActivity.this);
            btn.setText("WEIGHT");
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.weight_icon, 0, 0, 0);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bloodWeigthIntent = new Intent(PatientDiseaseActivity.this, PatientWeightTrackActivity.class);
                    startActivity(bloodWeigthIntent);
                }
            });

        }if( PatientActivity.isAllergy){
            Button btn=new Button(PatientDiseaseActivity.this);
            btn.setText("ALLERGY");
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_sneeze_40, 0, 0, 0);
            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent bloodWeigthIntent = new Intent(PatientDiseaseActivity.this, PatientAllergyTrackActivity.class);
                    startActivity(bloodWeigthIntent);
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

}
