package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShelfData {

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("shelfItemList")
    @Expose
    private ArrayList<ShelfItemList> shelfItemListArrayList;

    public ShelfData(String user_id, ArrayList<ShelfItemList> shelfItemListArrayList) {
        this.user_id = user_id;
        this.shelfItemListArrayList = shelfItemListArrayList;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<ShelfItemList> getShelfItemListArrayList() {
        return shelfItemListArrayList;
    }

    public void setShelfItemListArrayList(ArrayList<ShelfItemList> shelfItemListArrayList) {
        this.shelfItemListArrayList = shelfItemListArrayList;
    }
}
