package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DocDataList {

    @SerializedName("docName")
    @Expose
    private String docName;

    @SerializedName("field")
    @Expose
    private String field;

    @SerializedName("designation")
    @Expose
    private String designation;

    @SerializedName("ref_id")
    @Expose
    private String ref_id;

    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("availableHospitals")
    @Expose
    private ArrayList<AvailableHospital> availableHospital;

    @SerializedName("requestLists")
    @Expose
    private ArrayList<RequestList> requestLists;

    public DocDataList(String docName, String field, String designation, int rating, ArrayList<AvailableHospital> availableHospital) {
        this.docName = docName;
        this.field = field;
        this.designation = designation;
        this.rating = rating;
        this.availableHospital = availableHospital;
    }

    public DocDataList(String docName, String field, String designation, int rating, ArrayList<AvailableHospital> availableHospital, ArrayList<RequestList> requestLists) {
        this.docName = docName;
        this.field = field;
        this.designation = designation;
        this.rating = rating;
        this.availableHospital = availableHospital;
        this.requestLists = requestLists;
    }

    public DocDataList(String docName, String field, String designation, String ref_id, int rating, ArrayList<AvailableHospital> availableHospital, ArrayList<RequestList> requestLists) {
        this.docName = docName;
        this.field = field;
        this.designation = designation;
        this.ref_id = ref_id;
        this.rating = rating;
        this.availableHospital = availableHospital;
        this.requestLists = requestLists;
    }

    public DocDataList() {
    }

    public DocDataList(String docName, String field, String designation, int rating) {
        this.docName = docName;
        this.field = field;
        this.designation = designation;
        this.rating = rating;
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

    public ArrayList<AvailableHospital> getAvailableHospital() {
        return availableHospital;
    }

    public void setAvailableHospital(ArrayList<AvailableHospital> availableHospital) {
        this.availableHospital = availableHospital;
    }

    public ArrayList<RequestList> getRequestLists() {
        return requestLists;
    }

    public void setRequestLists(ArrayList<RequestList> requestLists) {
        this.requestLists = requestLists;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }
}
