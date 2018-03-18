package com.example.tuba.patienttrackingsystem;

/**
 * Created by BETUL MURA on 13.3.2018.
 */

public class DoctorMyAppointment {
    public String doctorName;
    public String departmentName;
    public String appointmentDate;
    public String appointmentTime;
    public String patientName;
    public String patientTc;

    public DoctorMyAppointment(String patientName, String patientTc, String appointmentDate, String appointmentTime) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.patientName = patientName;
        this.patientTc = patientTc;
    }
}
