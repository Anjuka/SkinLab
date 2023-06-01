package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DiaryFeedbackDataList {

    @SerializedName("compare_list")
    @Expose
    private ArrayList<DiaryDataList> compare_list;

    @SerializedName("feedback")
    @Expose
    private String feedback;

    public DiaryFeedbackDataList(ArrayList<DiaryDataList> compare_list, String feedback) {
        this.compare_list = compare_list;
        this.feedback = feedback;
    }

    public DiaryFeedbackDataList(){

    }

    public ArrayList<DiaryDataList> getCompare_list() {
        return compare_list;
    }

    public void setCompare_list(ArrayList<DiaryDataList> compare_list) {
        this.compare_list = compare_list;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
