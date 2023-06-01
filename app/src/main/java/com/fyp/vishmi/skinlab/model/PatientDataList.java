package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PatientDataList {

    @SerializedName("requestData")
    @Expose
    private ArrayList<RequestList> requestData;

    @SerializedName("nextConsultation")
    @Expose
    private ArrayList<String> nextConsultation;

    @SerializedName("note")
    @Expose
    private String note;

    public PatientDataList(ArrayList<RequestList> requestData, ArrayList<String> nextConsultation, String note) {
        this.requestData = requestData;
        this.nextConsultation = nextConsultation;
        this.note = note;
    }

    public PatientDataList() {
    }

    public ArrayList<RequestList> getRequestData() {
        return requestData;
    }

    public void setRequestData(ArrayList<RequestList> requestData) {
        this.requestData = requestData;
    }

    public ArrayList<String> getNextConsultation() {
        return nextConsultation;
    }

    public void setNextConsultation(ArrayList<String> nextConsultation) {
        this.nextConsultation = nextConsultation;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
