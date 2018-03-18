package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientEditProfileActivity extends AppCompatActivity {

    CircleImageView imageToUpload;
    private final static int PICK_IMAGE=100;
    static Uri imageUri;

    String base_url="http://192.168.156.169/Service1.svc/PatientEditProfile";

    EditText txt_patient_name, txt_patient_pwd, txt_patient_pnum, txt_patient_address, txt_patient_email,
            txt_patient_weight, txt_patient_height;
    Button update;
    String patient_name, patient_pwd, patient_pNumber, patient_email, patient_address;
    String patient_height, patient_weight;
    int patient_tc;
    TextView txt_patient_name2, txt_patient_address2, txt_patient_email2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);

        imageToUpload = (CircleImageView) findViewById(R.id.imageToUpload);

        imageToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        txt_patient_name2 = (TextView)findViewById(R.id.txt_dName2);
        txt_patient_address2 = (TextView)findViewById(R.id.txt_dAddress2);
        txt_patient_email2 = (TextView)findViewById(R.id.txt_dMail2);
        txt_patient_name = (EditText)findViewById(R.id.nameTxt);
        txt_patient_pwd = (EditText)findViewById(R.id.passwordTxt);
        txt_patient_address = (EditText)findViewById(R.id.addressTxt);
        txt_patient_email = (EditText)findViewById(R.id.emailTxt);
        txt_patient_height = (EditText)findViewById(R.id.heightTxt);
        txt_patient_weight = (EditText)findViewById(R.id.weightTxt);
        update = (Button)findViewById(R.id.btn_Dupdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patient_tc = Integer.parseInt(LoginActivity.girenPatient);
                patient_name = txt_patient_name.getText().toString();
                patient_pwd= txt_patient_pwd.getText().toString();
                patient_email = txt_patient_email.getText().toString();
                patient_pNumber = txt_patient_pnum.getText().toString();
                patient_address = txt_patient_address.getText().toString();
                patient_weight = txt_patient_weight.getText().toString();
                patient_height = txt_patient_height.getText().toString();
                //patient_height = Double.parseDouble(txt_patient_height.getText().toString());
                //patient_weight = Double.parseDouble(txt_patient_weight.getText().toString());

                new PatientEditProfileActivity.PatientEditProfile().execute(base_url+"/"+patient_tc+"/"+patient_name+"/"+patient_pwd+"/"+patient_pNumber+"/"+patient_email+"/"+patient_address+"/"+patient_weight+"/"+patient_height);


            }
        });
        txt_patient_pnum = (EditText)findViewById(R.id.teleponeTxt);
        txt_patient_pnum.addTextChangedListener(new PhoneNumberFormattingTextWatcher(){
            private boolean backspacingFlag = false;
            private boolean editedFlag = false;
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cursorComplement = s.length()-txt_patient_pnum.getSelectionStart();
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                String phone = string.replaceAll("[^\\d]", "");


                if (!editedFlag) {
                    if (phone.length() >= 7 && !backspacingFlag) {

                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 4) + ") " + phone.substring(4,7) + "-" + phone.substring(7);
                        txt_patient_pnum.setText(ans);
                        txt_patient_pnum.setSelection(txt_patient_pnum.getText().length()-cursorComplement);

                    } else if (phone.length() >= 4 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" +phone.substring(0, 4) + ") " + phone.substring(4);
                        txt_patient_pnum.setText(ans);
                        txt_patient_pnum.setSelection(txt_patient_pnum.getText().length()-cursorComplement);
                    }

                } else {
                    editedFlag = false;
                }
            }
        });

        new GetPatientDetails().execute("http://192.168.156.169/Service1.svc/PatientDetails");

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode==RESULT_OK &&requestCode==PICK_IMAGE ){
            imageUri = data.getData();
            imageToUpload.setImageURI(imageUri);
        }
    }


    class PatientEditProfile extends AsyncTask<String, Void, String> {
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
                j.put("Patient_Tc",patient_tc);
                j.put("Patient_Name",patient_name);
                j.put("Patient_Password",patient_pwd);
                j.put("Patient_PhoneNumber",patient_pNumber);
                j.put("Patient_Email",patient_email);
                j.put("Patient_Height",patient_height);
                j.put("Patient_Weight",patient_weight);



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
                Toast.makeText(PatientEditProfileActivity.this, "Patient updated successfully ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(PatientEditProfileActivity.this, "Patient does not update successfully ", Toast.LENGTH_LONG).show();
            }
        }
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
                        String pword = jsonArray.getJSONObject(i).getString("Patient_Password");
                        String phoneNumber = jsonArray.getJSONObject(i).getString("Patient_PhoneNumber");
                        String weight = jsonArray.getJSONObject(i).getString("Patient_Weight");
                        String height = jsonArray.getJSONObject(i).getString("Patient_Height");


                        if (LoginActivity.girenPatient.equals(patientTc)) {

                            txt_patient_name.setText(name);
                            txt_patient_email.setText(email);
                            txt_patient_address.setText(address);
                            txt_patient_pnum.setText(phoneNumber);
                            txt_patient_pwd.setText(pword);
                            txt_patient_weight.setText(weight);
                            txt_patient_height.setText(height);

                            txt_patient_name2.setText(name);
                            txt_patient_email2.setText(email);
                            txt_patient_address2.setText(address);

                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
