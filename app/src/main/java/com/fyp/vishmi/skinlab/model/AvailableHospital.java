package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableHospital {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("contact")
    @Expose
    private String contact;


    public AvailableHospital(String name, String address, String contact) {
        this.name = name;
        this.address = address;
        this.contact = contact;
    }



    public String getName() {
        return name;
    }

    public AvailableHospital(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
