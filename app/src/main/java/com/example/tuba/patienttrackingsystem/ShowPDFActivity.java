package com.example.tuba.patienttrackingsystem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class ShowPDFActivity extends AppCompatActivity {

    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf);

        pdfView=(PDFView)findViewById(R.id.pdfView);

        if(DoctorPatientAssayResultActivity.selectedItem.equals("sample.pdf")){
            pdfView.fromAsset("sample.pdf").load();
        }
        else if(DoctorPatientAssayResultActivity.selectedItem.equals("intro.pdf")){
            pdfView.fromAsset("intro.pdf").load();
        }

        
    }
}
