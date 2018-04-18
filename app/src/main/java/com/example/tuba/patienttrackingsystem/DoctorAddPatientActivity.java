package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.security.MessageDigest;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class DoctorAddPatientActivity extends AppCompatActivity {

    String password;
    ImageView image;
    String text2Qr;
    String AES="AES";

    int tc; String name; String pwd; String address; String gender; String email;
    int doctor_tc;
    String phone;  int height; int weigth; String birthdate;
    private DatePicker datePicker;
    static final int DATE_DIALOG_ID = 999;
    private EditText txt_birthdate=null;
    private int year;
    private int month;
    private int day;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add_patient);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/

        final EditText txt_tc= (EditText) findViewById(R.id.txt_patientTC);
        final EditText txt_name= (EditText) findViewById(R.id.txt_patientName);
        final EditText txt_pwd= (EditText) findViewById(R.id.txt_patientPassword);
        final EditText txt_email= (EditText) findViewById(R.id.txt_patientEmail);
        final EditText txt_address= (EditText) findViewById(R.id.txt_patientAddress);
        final EditText txt_phone= (EditText) findViewById(R.id.txt_patientPhone);
        txt_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(){
            private boolean backspacingFlag = false;
            private boolean editedFlag = false;
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                cursorComplement = s.length()-txt_phone.getSelectionStart();

                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing to do here =D
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                String phone = string.replaceAll("[^\\d]", "");


                if (!editedFlag) {
                    if (phone.length() >= 7 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 4) + ") " + phone.substring(4,7) + "-" + phone.substring(7);
                        txt_phone.setText(ans);
                        txt_phone.setSelection(txt_phone.getText().length()-cursorComplement);

                    } else if (phone.length() >= 4 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" +phone.substring(0, 4) + ") " + phone.substring(4);
                        txt_phone.setText(ans);
                        txt_phone.setSelection(txt_phone.getText().length()-cursorComplement);
                    }
                } else {
                    editedFlag = false;
                }
            }
        });

        final EditText txt_height= (EditText) findViewById(R.id.txt_patientHeight);
        final EditText txt_weight= (EditText) findViewById(R.id.txt_patientWeight);
        txt_birthdate= (EditText) findViewById(R.id.txt_patientBirthdate);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH)+1;
        day = c.get(Calendar.DAY_OF_MONTH);
        txt_birthdate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.txt_patientBirthdate:
                        showDialog(DATE_DIALOG_ID);
                        break;
                }
            }
        });

        radioGroup= (RadioGroup)findViewById(R.id.radioGroupGender);

        password = "123";
        image= (ImageView)findViewById(R.id.image);
        Button generateQR = (Button) findViewById(R.id.btn_patientQrButton);
        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    text2Qr = encrypt(txt_tc.getText().toString().trim(), password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                }
                catch(WriterException e){
                    e.printStackTrace();
                }


            }
        });

        Button register = (Button) findViewById(R.id.btn_patientRegButton);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tc= Integer.parseInt(txt_tc.getText().toString());
                doctor_tc=Integer.parseInt(LoginActivity.girenDoctor);
                name= txt_name.getText().toString();
                pwd= txt_pwd.getText().toString();
                email=txt_email.getText().toString();
                phone=txt_phone.getText().toString();
                address=txt_address.getText().toString();
                height=Integer.parseInt(txt_height.getText().toString());
                weigth=Integer.parseInt(txt_weight.getText().toString());
                int checked= radioGroup.getCheckedRadioButtonId();
                switch (checked){
                    case R.id.radioBtn_male: {gender="Male"; break;}
                    case R.id.radioBtn_female: {gender="Female"; break;}
                }


                new PatientRegister().execute("http://192.168.156.169/Service1.svc/PatientRegister/New");
                new DoctorPatientRelation().execute("http://192.168.156.169/Service1.svc/DoctorPatientRelation/New");
                //new PatientRegister().execute("http://192.168.0.10/Service1.svc/PatientRegister/New");
                //new DoctorPatientRelation().execute("http://192.168.0.10/Service1.svc/DoctorPatientRelation/New");

            }

        });

        
    }

    

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, year,month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =  new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth)
        {
            year = selectedYear;
            month = monthOfYear+1;
            day = dayOfMonth;

            txt_birthdate.setText(new StringBuilder().append(padding_str(day))
                    .append("/").append(padding_str(month))
                    .append("/").append(padding_str(year))
            );
            birthdate= txt_birthdate.getText().toString();
        }
    };

    private static String padding_str(int c)
    {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    class PatientRegister extends AsyncTask<String, Void, String> {
        String status = null;

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn;
            BufferedReader reader;


            try {
                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);
                conn.addRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");

                JSONObject j = new JSONObject();
                j.put("Patient_Tc",tc);
                j.put("Patient_Name",name);
                j.put("Patient_Password",pwd);
                j.put("Patient_Gender",gender);
                j.put("Patient_Adress",address);
                j.put("Patient_PhoneNumber",phone);
                j.put("Patient_Email",email);
                j.put("Patient_Heigth",height);
                j.put("Patient_Weight",weigth);
                j.put("Patient_DateOfBirth",birthdate);

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
                Toast.makeText(DoctorAddPatientActivity.this, "Patient save successfully ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(DoctorAddPatientActivity.this, "Patient does not save successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }


    class DoctorPatientRelation extends AsyncTask<String, Void, String> {
        String status = null;

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... connUrl) {
            HttpURLConnection conn;
            BufferedReader reader;


            try {
                final URL url = new URL(connUrl[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setChunkedStreamingMode(0);
                conn.addRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");

                JSONObject j = new JSONObject();
                j.put("Patient_Tc",tc);
                j.put("DoctorTc",doctor_tc);

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
                Toast.makeText(DoctorAddPatientActivity.this, "Patient - Doctor Relation ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(DoctorAddPatientActivity.this, "Patient does not save successfully to Patient - Doctor Relation ", Toast.LENGTH_LONG).show();
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

}


