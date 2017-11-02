package com.example.tuba.patienttrackingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class PatientDiseaseActivity extends AppCompatActivity {
    CardView diseaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_disease);

        diseaseButton = (CardView)findViewById(R.id.diseaseButton);
        diseaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });


    }
    private void check(){
        final Intent i = new Intent(this,PatientDiseaseActivity.class);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(0);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    startActivity(i);
                    finish();

                }
            }
        };
        timer.start();
    }

}
