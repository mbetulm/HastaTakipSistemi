package com.example.tuba.patienttrackingsystem;

/**
 * Created by BETUL MURA on 13.3.2018.
 */

public class PatientMyAppointment {
    public String doctorName;
    public String departmentName;
    public String appointmentDate;
    public String appointmentTime;
    public String patientName;
    public String patientTc;



    public PatientMyAppointment(String departmentName, String doctorName , String appointmentDate, String appointmentTime) {
        this.doctorName = doctorName;
        this.departmentName = departmentName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }


}
