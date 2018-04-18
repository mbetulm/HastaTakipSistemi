package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText userName;
    EditText password;
    ArrayList<HashMap<String ,String>> DoctorList;
    public static String girenDoctor="";
    public static String girenPatient="";
    public static String girenPatientName="";
    public static String girenPatientGender="";
    public static String girenPatientDateOfBirth="";
    public static String girenPatientHeight="";
    public static String girenPatientWeight="";

    int basarılı_doctor=0;
    int basarılı_hasta=0;

    TextView showPwd ;
    int setType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setType= 1;
        userName = (EditText)findViewById(R.id.txt_username);
        password = (EditText)findViewById(R.id.txt_password);
        Button login = (Button)findViewById(R.id.btn_Login);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v ){
                new GetDoctorList().execute("http://192.168.156.169/Service1.svc/DoctorLogin");
                //new GetDoctorList().execute("http://192.168.0.10/Service1.svc/DoctorLogin");
            }
        });
        DoctorList = new ArrayList<HashMap<String, String>>();

        showPwd = (TextView)findViewById(R.id.eye);
        showPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(setType == 1 ){
                    setType = 0;
                    password.setTransformationMethod(null);
                    if (password.getText().length() > 0)
                        password.setSelection(password.getText().length());

                }else{
                    setType = 1;
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    if (password.getText().length() > 0 )
                        password.setSelection(password.getText().length());
                }
            }
        });

    }


    class GetDoctorList extends AsyncTask<String, Void, String> {
        String status = null;

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn;
            BufferedReader reader;

            try{
                final URL url= new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();
                if(result==200){
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine())!= null){
                        status = line;
                    }
                }

            }catch (Exception ex){

                System.out.print(ex);


            }
            return status;
        }


        public void onPostExecute(String result){
            super.onPostExecute(result);

            if (result!=null){
                try{

                    JSONArray jsonArray = new JSONArray(result);
                    for (int i =0; i<jsonArray.length(); i++)
                     {
                         String pwd= jsonArray.getJSONObject(i).getString("DoctorPassword").trim();
                         String name= jsonArray.getJSONObject(i).getString("DoctorName").trim();
                         String doctorTc= jsonArray.getJSONObject(i).getString("DoctorTc").trim();

                         if(userName.getText().toString().trim().equals(name) && password.getText().toString().trim().equals(pwd)){
                             Intent intent = new Intent(LoginActivity.this, DoctorActivity.class);
                             startActivity(intent);
                             Toast.makeText(LoginActivity.this, "Doctor Successful login", Toast.LENGTH_LONG).show();
                             girenDoctor=doctorTc;
                             basarılı_doctor=1;
                             break;
                         }
                     }
                     if(basarılı_doctor==0){
                         new GetPatientList().execute("http://192.168.156.169/Service1.svc/PatientLogin");
                         //new GetPatientList().execute("http://192.168.0.10/Service1.svc/PatientLogin");

                     }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }


    }

    class GetPatientList extends AsyncTask<String,Void,String>{
        String status = null;

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn;
            BufferedReader reader;

            try{
                final URL url= new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.addRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");
                int result = conn.getResponseCode();
                if(result==200){
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine())!= null){
                        status = line;
                    }
                }

            }catch (Exception ex){

                System.out.print(ex);


            }
            return status;
        }


        public void onPostExecute(String result){
            super.onPostExecute(result);
            if (result!=null){
                try{

                    JSONArray jsonArray = new JSONArray(result);
                    for (int i =0; i<jsonArray.length(); i++)
                    {
                        String pwd= jsonArray.getJSONObject(i).getString("Patient_Password").trim();
                        String name= jsonArray.getJSONObject(i).getString("Patient_Name").trim();
                        String patientTc= jsonArray.getJSONObject(i).getString("Patient_Tc").trim();
                        String patientGender = jsonArray.getJSONObject(i).getString("Patient_Gender").trim();
                        String patientDOB = jsonArray.getJSONObject(i).getString("Patient_DateOfBirth").trim();
                        String patientHeight = jsonArray.getJSONObject(i).getString("Patient_Height").trim();
                        String patientWeight = jsonArray.getJSONObject(i).getString("Patient_Weight").trim();

                        if(userName.getText().toString().trim().equals(name) && password.getText().toString().trim().equals(pwd)){
                            Intent intent = new Intent(LoginActivity.this, PatientActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Patient Successful login", Toast.LENGTH_LONG).show();
                            girenPatient=patientTc;
                            girenPatientName=name;
                            girenPatientGender=patientGender;
                            girenPatientDateOfBirth=patientDOB;
                            girenPatientHeight=patientHeight;
                            girenPatientWeight=patientWeight;

                            basarılı_hasta=1;
                            break;
                        }
                    }
                    if(basarılı_hasta==0 && basarılı_doctor==0){
                        Toast.makeText(LoginActivity.this, "Unsuccesfull login", Toast.LENGTH_LONG).show();

                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

    }
}
