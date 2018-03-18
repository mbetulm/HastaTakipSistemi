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
import java.sql.Blob;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorEditProfileActivity extends AppCompatActivity {
    CircleImageView imageToUpload;
    private final static int PICK_IMAGE=100;
    static Uri imageUri;

    String base_url="http://192.168.156.169/Service1.svc/DoctorEditProfile";

    EditText txt_dname, txt_dpwd, txt_dpnum, txt_daddress, txt_demail;
    Button update;
    String name, pwd, pNumber, d_email, d_address;
    int d_tc;
    TextView txt_dname2, txt_daddress2, txt_demail2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_edit_profile);
        imageToUpload = (CircleImageView) findViewById(R.id.imageToUpload);

        imageToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        txt_dname2 = (TextView)findViewById(R.id.txt_dName2);
        txt_daddress2 = (TextView)findViewById(R.id.txt_dAddress2);
        txt_demail2 = (TextView)findViewById(R.id.txt_dMail2);
        txt_dname = (EditText)findViewById(R.id.nameTxt);
        txt_dpwd = (EditText)findViewById(R.id.passwordTxt);
        txt_daddress = (EditText)findViewById(R.id.addressTxt);
        txt_demail = (EditText)findViewById(R.id.emailTxt);
        update = (Button)findViewById(R.id.btn_Dupdate);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d_tc=Integer.parseInt(LoginActivity.girenDoctor);
                name= txt_dname.getText().toString();
                pwd= txt_dpwd.getText().toString();
                d_email= txt_demail.getText().toString();
                pNumber= txt_dpnum.getText().toString();
                d_address= txt_daddress.getText().toString();

                new DoctorEditProfile().execute(base_url+"/"+d_tc+"/"+name+"/"+pwd+"/"+pNumber+"/"+d_email+"/"+d_address);


            }
        });
        txt_dpnum = (EditText)findViewById(R.id.teleponeTxt);
        txt_dpnum.addTextChangedListener(new PhoneNumberFormattingTextWatcher(){
            private boolean backspacingFlag = false;
            private boolean editedFlag = false;
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cursorComplement = s.length()-txt_dpnum.getSelectionStart();
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
                        txt_dpnum.setText(ans);
                        txt_dpnum.setSelection(txt_dpnum.getText().length()-cursorComplement);

                    } else if (phone.length() >= 4 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" +phone.substring(0, 4) + ") " + phone.substring(4);
                        txt_dpnum.setText(ans);
                        txt_dpnum.setSelection(txt_dpnum.getText().length()-cursorComplement);
                    }

                } else {
                    editedFlag = false;
                }
            }
        });


       new GetDoctorDetails().execute("http://192.168.156.169/Service1.svc/DoctorDetails");


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


    class DoctorEditProfile extends AsyncTask<String, Void, String> {
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
                j.put("DoctorTc",d_tc);
                j.put("DoctorName",name);
                j.put("DoctorPassword",pwd);
                j.put("PhoneNumber",pNumber);
                j.put("Email",d_email);
                j.put("Address",d_address);



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
                Toast.makeText(DoctorEditProfileActivity.this, "Doctor updated successfully ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(DoctorEditProfileActivity.this, "Doctor does not update successfully ", Toast.LENGTH_LONG).show();
            }
        }
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
                        String pword = jsonArray.getJSONObject(i).getString("DoctorPassword");
                        String email = jsonArray.getJSONObject(i).getString("Email");
                        String address = jsonArray.getJSONObject(i).getString("Address");
                        String phoneNumber = jsonArray.getJSONObject(i).getString("PhoneNumber");
                        String doctorTc = jsonArray.getJSONObject(i).getString("DoctorTc");


                        if (LoginActivity.girenDoctor.equals(doctorTc)) {

                            txt_dname.setText(name);
                            txt_demail.setText(email);
                            txt_daddress.setText(address);
                            txt_dpnum.setText(phoneNumber);
                            txt_dpwd.setText(pword);
                            txt_dname2.setText(name);
                            txt_demail2.setText(email);
                            txt_daddress2.setText(address);

                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

