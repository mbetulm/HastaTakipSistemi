package com.example.tuba.patienttrackingsystem;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hsalf.smilerating.SmileRating;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PatientAllergyTrackActivity extends AppCompatActivity {

    SmileRating cough, wheeze, e_cough, breath, inhalation, runny_nose, nasal_obstruction, sneeze, nasal_itch, throat_itch,
            eye_itch, watering_of_eyes, eye_redness, fever;
    private static final int Disease_ID = 5;
    Button submit;
    public int score,avgScore;
    public static String time;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_allergy_track);

        cough = (SmileRating)findViewById(R.id.cough);
        cough.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += cough.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += cough.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += cough.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += cough.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += cough.getRating();
                        break;
                }
            }
        });


        wheeze = (SmileRating)findViewById(R.id.wheeze);
        wheeze.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += wheeze.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += wheeze.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += wheeze.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += wheeze.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += wheeze.getRating();
                        break;
                }
            }
        });

        e_cough = (SmileRating)findViewById(R.id.e_cough);
        e_cough.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += e_cough.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += e_cough.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += e_cough.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += e_cough.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += e_cough.getRating();
                        break;
                }
            }
        });

        breath = (SmileRating)findViewById(R.id.breath);
        breath.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += breath.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += breath.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += breath.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += breath.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += breath.getRating();
                        break;
                }
            }
        });

        inhalation = (SmileRating)findViewById(R.id.inhalation);
        inhalation.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += inhalation.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += inhalation.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += inhalation.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += inhalation.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += inhalation.getRating();
                        break;
                }
            }
        });

        runny_nose = (SmileRating)findViewById(R.id.runny_nose);
        runny_nose.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += runny_nose.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += runny_nose.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += runny_nose.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += runny_nose.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += runny_nose.getRating();
                        break;
                }
            }
        });

        nasal_obstruction = (SmileRating)findViewById(R.id.nasal_obstruction);
        nasal_obstruction.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += nasal_obstruction.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += nasal_obstruction.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += nasal_obstruction.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += nasal_obstruction.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += nasal_obstruction.getRating();
                        break;
                }
            }
        });

        sneeze = (SmileRating)findViewById(R.id.sneeze);
        sneeze.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += sneeze.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += sneeze.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += sneeze.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += sneeze.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += sneeze.getRating();
                        break;
                }
            }
        });

        nasal_itch = (SmileRating)findViewById(R.id.nasal_itch);
        nasal_itch.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += nasal_itch.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += nasal_itch.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += nasal_itch.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += nasal_itch.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += nasal_itch.getRating();
                        break;
                }
            }
        });

        throat_itch = (SmileRating)findViewById(R.id.throat_itch);
        throat_itch.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += throat_itch.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += throat_itch.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += throat_itch.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += throat_itch.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += throat_itch.getRating();
                        break;
                }
            }
        });

        eye_itch = (SmileRating)findViewById(R.id.eye_itch);
        eye_itch.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += eye_itch.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += eye_itch.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += eye_itch.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += eye_itch.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += eye_itch.getRating();
                        break;
                }
            }
        });

        watering_of_eyes = (SmileRating)findViewById(R.id.Watering_of_eyes);
        watering_of_eyes.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += watering_of_eyes.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += watering_of_eyes.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += watering_of_eyes.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += watering_of_eyes.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += watering_of_eyes.getRating();
                        break;
                }
            }
        });

        eye_redness = (SmileRating)findViewById(R.id.eye_redness);
        eye_redness.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += eye_redness.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += eye_redness.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += eye_redness.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += eye_redness.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += eye_redness.getRating();
                        break;
                }
            }
        });

        fever = (SmileRating)findViewById(R.id.fever);
        fever.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley) {
                switch (smiley){
                    case SmileRating.BAD:
                        score += fever.getRating();
                        break;
                    case SmileRating.GOOD:
                        score += fever.getRating();
                        break;
                    case SmileRating.GREAT:
                        score += fever.getRating();
                        break;
                    case SmileRating.OKAY:
                        score += fever.getRating();
                        break;
                    case SmileRating.TERRIBLE:
                        score += fever.getRating();
                        break;
                }
            }
        });

        Calendar takvim = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        time = (sdf.format(takvim.getTime()));
        final String h = time.substring(0, 2);
        String m = time.substring(3, 5);
        final int get_time = Integer.parseInt(h.concat(m));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        date = (sdf2.format(calendar.getTime()));

        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score = fever.getRating() + cough.getRating() + wheeze.getRating() + e_cough.getRating() + breath.getRating() +
                        inhalation.getRating() + runny_nose.getRating() + nasal_obstruction.getRating() + sneeze.getRating() +
                        nasal_itch.getRating() + throat_itch.getRating() + eye_itch.getRating() + watering_of_eyes.getRating() +
                        eye_redness.getRating();
                avgScore = score/14;
                Toast.makeText(PatientAllergyTrackActivity.this, String.valueOf(avgScore), Toast.LENGTH_SHORT ).show();
                if (600 < get_time && get_time < 2400) {
                    new PatientAllergyTrack().execute("http://192.168.156.169/Service1.svc/PatientAllergyTrack");
                }
            }
        });

    }

    class PatientAllergyTrack extends AsyncTask<String, Void, String> {
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
                j.put("Patient_Tc", LoginActivity.girenPatient);
                j.put("Allergy_score", avgScore);
                j.put("DiseaseID", Disease_ID);
                j.put("Date", date);
                j.put("Time", time);


                OutputStream out = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
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
                Toast.makeText(PatientAllergyTrackActivity.this, "disease relation successfully ", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PatientAllergyTrackActivity.this, "disease relation does not successfully ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
