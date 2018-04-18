package com.example.tuba.patienttrackingsystem;

/**
 * Created by BETUL MURA on 29.3.2018.
 */

public class DoctorGetRelationReport {

    public String systemDate; // end track
    public String start_track;
    public String patient_tc;
    public String patient_name;
    public String disease_name;

    public DoctorGetRelationReport(String systemDate, String patient_tc, String patient_name, String disease_name, String startTrack) {
        this.systemDate = systemDate;
        this.patient_tc = patient_tc;
        this.patient_name = patient_name;
        this.disease_name = disease_name;
        this.start_track = startTrack;
    }

}
