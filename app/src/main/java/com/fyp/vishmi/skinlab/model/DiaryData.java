package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DiaryData {

    @SerializedName("DiaryDataList")
    @Expose
    private ArrayList<DiaryDataList> diaryDataLists;

    @SerializedName("id")
    @Expose
    private String id;

    public DiaryData(ArrayList<DiaryDataList> diaryDataLists) {
        this.diaryDataLists = diaryDataLists;
    }

    public DiaryData(ArrayList<DiaryDataList> diaryDataLists, String id) {
        this.diaryDataLists = diaryDataLists;
        this.id = id;
    }

    public DiaryData() {
    }

    public ArrayList<DiaryDataList> getDiaryDataLists() {
        return diaryDataLists;
    }

    public void setDiaryDataLists(ArrayList<DiaryDataList> diaryDataLists) {
        this.diaryDataLists = diaryDataLists;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
