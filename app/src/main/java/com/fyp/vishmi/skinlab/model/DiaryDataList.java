package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiaryDataList {

    @SerializedName("img_url")
    @Expose
    private String img_url;

    @SerializedName("capture_date")
    @Expose
    private String capture_date;

    @SerializedName("capture_time")
    @Expose
    private String capture_time;

    public DiaryDataList(String img_url, String capture_date, String capture_time) {
        this.img_url = img_url;
        this.capture_date = capture_date;
        this.capture_time = capture_time;
    }

    public DiaryDataList() {
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getCapture_date() {
        return capture_date;
    }

    public void setCapture_date(String capture_date) {
        this.capture_date = capture_date;
    }

    public String getCapture_time() {
        return capture_time;
    }

    public void setCapture_time(String capture_time) {
        this.capture_time = capture_time;
    }
}
