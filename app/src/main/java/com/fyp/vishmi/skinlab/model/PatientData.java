package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PatientData {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("docDataList")
    @Expose
    private ArrayList<PatientDataList> patientDataLists;

    public PatientData(String user_id, ArrayList<PatientDataList> patientDataLists) {
        this.user_id = user_id;
        this.patientDataLists = patientDataLists;
    }

    public PatientData() {
    }

    public ArrayList<PatientDataList> getPatientDataLists() {
        return patientDataLists;
    }

    public void setPatientDataLists(ArrayList<PatientDataList> patientDataLists) {
        this.patientDataLists = patientDataLists;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
