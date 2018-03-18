package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.methods.HttpPost;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import static android.view.Menu.FIRST;

public class DoctorMyPatientsActivity extends AppCompatActivity {
    ListView list;
    ArrayList<HashMap<String ,String>> PatientList;
    ListViewAdapter adapter;
    String base_url="http://192.168.156.169/Service1.svc/DeletePatient";
    public static String selectedItem="";
    public static String selected_TC;

    public static final String FIRST_COLUMN="Patient_Name";
    public static final String SECOND_COLUMN="Patient_Tc";

    EditText editText;
    private static ArrayList<HashMap<String, String >> searchResults;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_my_patients);
        editText = (EditText)findViewById(R.id.txt_search);
        list = (ListView) findViewById(R.id.listView1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Track
                Intent intent = new Intent(DoctorMyPatientsActivity.this, DoctorTrackActivity.class);
                startActivity(intent);

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //delete
                new DeleteRelation().execute(base_url+"/"+selected_TC);
                PatientList.clear();
                new GetMyPatients(DoctorMyPatientsActivity.this, list).execute("http://192.168.156.169/Service1.svc/MyPatients");

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //show
            }
        });





        PatientList = new ArrayList<HashMap<String, String>>();
        new GetMyPatients(DoctorMyPatientsActivity.this, list).execute("http://192.168.156.169/Service1.svc/MyPatients");
        //new GetMyPatients(DoctorMyPatientsActivity.this, list).execute("http://192.168.0.10/Service1.svc/MyPatients");


        searchResults = new ArrayList<HashMap<String,String>>();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                list = (ListView) findViewById(R.id.listView1);
                int textlength = editText.getText().length();
                String searchString= editText.getText().toString();
                searchResults.clear();
                String attr = null;
                String attr2= null;
                for (int p = 0; p < PatientList.size(); p++)
                {

                    attr = PatientList.get(p).toString().trim();
                    attr2 = attr.substring(14);
                    Log.i("attr2", attr2+"");

                    if (textlength  <= attr2.length())
                    {

                        if (searchString.equalsIgnoreCase(attr2.substring(0,textlength)))
                        {
                            searchResults.add(PatientList.get(p));

                        }
                        adapter = new ListViewAdapter(DoctorMyPatientsActivity.this, searchResults);
                        list.setAdapter(adapter);


                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
                String[] parts = selectedItem.split(",");
                String part1 = parts[1];
                selected_TC = part1.substring(12,part1.length()-1);

            }
        });


    }




    class GetMyPatients extends AsyncTask<String, Void, String> {
        String status=null;
        Activity context;
        ListView listView;

        public GetMyPatients (Activity context, ListView listView){
            this.context = context;
            this.listView = listView;
        }

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
                        String pName = jsonArray.getJSONObject(i).getString("Patient_Name");
                        String dTc= jsonArray.getJSONObject(i).getString("DoctorTc");
                        String ptTc = jsonArray.getJSONObject(i).getString("Patient_Tc");

                        HashMap<String,String> pList = new HashMap<String, String>();

                        if(LoginActivity.girenDoctor.equals(dTc)){
                            pList.put(FIRST_COLUMN,pName);
                            pList.put(SECOND_COLUMN,ptTc);
                            PatientList.add(pList);

                        }
                    }
                    adapter = new ListViewAdapter(DoctorMyPatientsActivity.this, PatientList);
                    listView.setAdapter(adapter);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }




    class DeleteRelation extends AsyncTask<String,Void,String>{

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
                conn.setRequestMethod("POST");
                 conn.setDoInput(true);
                //conn.setChunkedStreamingMode(0);
                conn.setRequestProperty("Content-Type", "application/json");

                JSONObject j = new JSONObject();
                j.put("Patient_Tc",selected_TC);


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
                Toast.makeText(DoctorMyPatientsActivity.this, "Patient deleted successfully ", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(DoctorMyPatientsActivity.this, "Patient does not delete successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
