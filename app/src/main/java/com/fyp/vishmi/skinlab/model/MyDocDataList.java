package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyDocDataList {

    @SerializedName("docName")
    @Expose
    private String docName;

    @SerializedName("field")
    @Expose
    private String field;

    @SerializedName("designation")
    @Expose
    private String designation;

    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("availableHospitals")
    @Expose
    private ArrayList<AvailableHospital> availableHospitals;

    @SerializedName("next_consultation")
    @Expose
    private ArrayList<String> next_consultation;

    @SerializedName("past_consultation")
    @Expose
    private String past_consultation;

    @SerializedName("package_select")
    @Expose
    private String package_select;

    public MyDocDataList(String docName, String field, String designation, int rating) {
        this.docName = docName;
        this.field = field;
        this.designation = designation;
        this.rating = rating;
    }

    public MyDocDataList(String docName, String field, String designation, int rating, ArrayList<AvailableHospital> availableHospitals, ArrayList<String> next_consultation, String past_consultation, String package_select) {
        this.docName = docName;
        this.field = field;
        this.designation = designation;
        this.rating = rating;
        this.availableHospitals = availableHospitals;
        this.next_consultation = next_consultation;
        this.past_consultation = past_consultation;
        this.package_select = package_select;
    }

    public MyDocDataList(){

    }

    public ArrayList<AvailableHospital> getAvailableHospitals() {
        return availableHospitals;
    }

    public void setAvailableHospitals(ArrayList<AvailableHospital> availableHospitals) {
        this.availableHospitals = availableHospitals;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<String> getNext_consultation() {
        return next_consultation;
    }

    public void setNext_consultation(ArrayList<String> next_consultation) {
        this.next_consultation = next_consultation;
    }

    public String getPast_consultation() {
        return past_consultation;
    }

    public void setPast_consultation(String past_consultation) {
        this.past_consultation = past_consultation;
    }

    public String getPackage_select() {
        return package_select;
    }

    public void setPackage_select(String package_select) {
        this.package_select = package_select;
    }
}
