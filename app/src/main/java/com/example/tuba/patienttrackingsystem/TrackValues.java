package com.example.tuba.patienttrackingsystem;

/**
 * Created by BETUL MURA on 29.3.2018.
 */

public class TrackValues {

    String Date,morning, afternoon, evening;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMorning(){
        return morning;
    }
    public void setMorning(String morning){
        this.morning=morning;
    }

    public String getAfternoon(){
        return afternoon;
    }
    public void setAfternoon(String afternoon){
        this.afternoon=afternoon;
    }

    public String getEvening(){
        return evening;
    }

    public void setEvening(String evening){
        this.evening=evening;
    }

}
