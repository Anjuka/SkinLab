package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DiaryFeedbackData {

    @SerializedName("DiaryFeedbackDataList")
    @Expose
    private ArrayList<DiaryFeedbackDataList> diaryFeedbackDataLists;

    public DiaryFeedbackData(ArrayList<DiaryFeedbackDataList> diaryFeedbackDataLists) {
        this.diaryFeedbackDataLists = diaryFeedbackDataLists;
    }

    public DiaryFeedbackData(){

    }

    public ArrayList<DiaryFeedbackDataList> getDiaryFeedbackDataLists() {
        return diaryFeedbackDataLists;
    }

    public void setDiaryFeedbackDataLists(ArrayList<DiaryFeedbackDataList> diaryFeedbackDataLists) {
        this.diaryFeedbackDataLists = diaryFeedbackDataLists;
    }
}
