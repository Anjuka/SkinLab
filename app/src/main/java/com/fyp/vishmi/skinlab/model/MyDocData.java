package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyDocData {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("docDataList")
    @Expose
    private ArrayList<MyDocDataList> docDataLists;

    public MyDocData(String user_id, ArrayList<MyDocDataList> docDataLists) {
        this.user_id = user_id;
        this.docDataLists = docDataLists;
    }

    public MyDocData() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<MyDocDataList> getDocDataLists() {
        return docDataLists;
    }

    public void setDocDataLists(ArrayList<MyDocDataList> docDataLists) {
        this.docDataLists = docDataLists;
    }
}
