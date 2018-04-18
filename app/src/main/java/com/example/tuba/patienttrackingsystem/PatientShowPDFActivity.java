package com.example.tuba.patienttrackingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class PatientShowPDFActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_show_pdf);

        pdfView=(PDFView)findViewById(R.id.pdfView);

        if(PatientAssayActivity.selectedItem.equals("sample.pdf")){
            pdfView.fromAsset("sample.pdf").load();
        }
        else if(PatientAssayActivity.selectedItem.equals("intro.pdf")){
            pdfView.fromAsset("intro.pdf").load();
        }
    }
}
