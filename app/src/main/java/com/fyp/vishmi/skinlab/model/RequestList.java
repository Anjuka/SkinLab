package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestList {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("age")
    @Expose
    private String age;

    @SerializedName("pack")
    @Expose
    private String pack;

    public RequestList(String id, String name, String age, String pack) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.pack = pack;
    }

    public RequestList() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }
}
