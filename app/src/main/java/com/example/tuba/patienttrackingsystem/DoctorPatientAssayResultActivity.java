package com.example.tuba.patienttrackingsystem;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.IOException;
import java.util.ArrayList;

public class DoctorPatientAssayResultActivity extends AppCompatActivity {

    ListView lv;
    PDFView pdfView;
    String[] files;
    String []items;
    static String selectedItem;

    ArrayList<String> arrayOfUsers = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_assay_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        lv=(ListView)findViewById(R.id.lv);

        AssetManager assetManager = getAssets();
        try {
            files = assetManager.list("Files");
            items=new String[files.length];

            for(int i=0; i<files.length; i++){
                items[i]=(files[i]);
                arrayOfUsers.add(items[i]);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        CustomPDFAdapter adapter = new CustomPDFAdapter(this,arrayOfUsers);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem=parent.getItemAtPosition(position).toString();
                Intent i = new Intent(DoctorPatientAssayResultActivity.this,ShowPDFActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}


