package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DocData {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("docDataList")
    @Expose
    private ArrayList<DocDataList> docDataLists;

    public DocData(String user_id, ArrayList<DocDataList> docDataLists) {
        this.user_id = user_id;
        this.docDataLists = docDataLists;
    }

    public DocData() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<DocDataList> getDocDataLists() {
        return docDataLists;
    }

    public void setDocDataLists(ArrayList<DocDataList> docDataLists) {
        this.docDataLists = docDataLists;
    }
}
