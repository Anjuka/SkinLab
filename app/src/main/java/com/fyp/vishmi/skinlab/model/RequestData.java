package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RequestData {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("docDataList")
    @Expose
    private ArrayList<RequestList> docDataLists;

    public RequestData(String user_id, ArrayList<RequestList> docDataLists) {
        this.user_id = user_id;
        this.docDataLists = docDataLists;
    }

    public RequestData() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<RequestList> getDocDataLists() {
        return docDataLists;
    }

    public void setDocDataLists(ArrayList<RequestList> docDataLists) {
        this.docDataLists = docDataLists;
    }
}
