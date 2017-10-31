package com.example.tuba.patienttrackingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText userName;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText)findViewById(R.id.txt_username);
        password = (EditText)findViewById(R.id.txt_password);
        Button login = (Button)findViewById(R.id.btn_Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    private void checkLogin(){
        if (userName.getText().toString().equals("doktor") && password.getText().toString().equals("1234")) {

            final Intent i = new Intent(this, DoctorActivity.class);
            Thread timer = new Thread(){
                public void run(){
                    try{
                        sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally{
                        startActivity(i);
                        finish();

                    }
                }
            };

            timer.start();
        }
        if(userName.getText().toString().equals("patient") && password.getText().toString().equals("1234")){

            final Intent i = new Intent(this, PatientActivity.class);
            Thread timer = new Thread(){
                public void run(){
                    try{
                        sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally{
                        startActivity(i);
                        finish();

                    }
                }
            };

            timer.start();
        }
    }
}
