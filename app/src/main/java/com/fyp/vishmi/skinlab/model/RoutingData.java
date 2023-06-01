package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RoutingData {

    @SerializedName("isCreated")
    @Expose
    private boolean isCreated;

    @SerializedName("id")
    @Expose
    private String id;

    public RoutingData(boolean isCreated, String id) {
        this.isCreated = isCreated;
        this.id = id;
    }

    public RoutingData() {
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
