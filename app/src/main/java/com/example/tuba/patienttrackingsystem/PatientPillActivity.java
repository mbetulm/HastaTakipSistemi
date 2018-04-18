package com.example.tuba.patienttrackingsystem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PatientPillActivity extends AppCompatActivity {

    ListView lv;
    DrugListAdapter customListAdapter;
    List<PatientMyDrugs> drugList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_pill);

        lv=(ListView) findViewById(R.id.drugsListview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        new GetPatientPill().execute("http://192.168.138.75/Service1.svc/GetMyDrugs");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


    class GetPatientPill extends AsyncTask<String, Void, String> {
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
                        String drugName1 = jsonArray.getJSONObject(i).getString("Drug1");
                        String drugName2 = jsonArray.getJSONObject(i).getString("Drug2");
                        String drugName3 = jsonArray.getJSONObject(i).getString("Drug3");
                        String drugName4 = jsonArray.getJSONObject(i).getString("Drug4");
                        String drugName5 = jsonArray.getJSONObject(i).getString("Drug5");
                        String pTc = jsonArray.getJSONObject(i).getString("Patient_Tc");


                        if (LoginActivity.girenPatient.equals(pTc)) {
                            drugList.add(new PatientMyDrugs(drugName1, drugName2, drugName3, drugName4, drugName5));
                        }
                    }
                    customListAdapter = new DrugListAdapter(getApplicationContext(), drugList);
                    lv.setAdapter(customListAdapter);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}

class PatientMyDrugs {
    String drugName1;
    String drugName2;
    String drugName3;
    String drugName4;
    String drugName5;

    public PatientMyDrugs(String drugName1, String drugName2, String drugName3, String drugName4, String drugName5) {
        this.drugName1 = drugName1;
        this.drugName2 = drugName2;
        this.drugName3 = drugName3;
        this.drugName4 = drugName4;
        this.drugName5 = drugName5;
    }


}
