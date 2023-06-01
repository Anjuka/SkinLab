package com.fyp.vishmi.skinlab.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShelfItemList implements Parcelable {

    /**
     *                     user.put("fName", full_name);
     *                     user.put("brand", brand);
     *                     user.put("manifac_date", manifac_date);
     *                     user.put("ex_date", ex_date);
     *                     user.put("open_date", open_date);
     *                     user.put("note", note);
     */

    @SerializedName("fName")
    @Expose
    private String fName;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("manifac_date")
    @Expose
    private String manifac_date;

    @SerializedName("ex_date")
    @Expose
    private String ex_date;

    @SerializedName("open_date")
    @Expose
    private String open_date;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("img_url")
    @Expose
    private String img_url;

    public ShelfItemList(String fName, String brand, String manifac_date, String ex_date, String open_date, String note) {
        this.fName = fName;
        this.brand = brand;
        this.manifac_date = manifac_date;
        this.ex_date = ex_date;
        this.open_date = open_date;
        this.note = note;
    }

    public ShelfItemList(String fName, String brand, String manifac_date, String ex_date, String open_date, String note, int rating) {
        this.fName = fName;
        this.brand = brand;
        this.manifac_date = manifac_date;
        this.ex_date = ex_date;
        this.open_date = open_date;
        this.note = note;
        this.rating = rating;
    }

    protected ShelfItemList(Parcel in) {
        fName = in.readString();
        brand = in.readString();
        manifac_date = in.readString();
        ex_date = in.readString();
        open_date = in.readString();
        note = in.readString();
        rating = in.readInt();
        img_url = in.readString();
    }

    public ShelfItemList(String fName, String brand, String manifac_date, String ex_date, String open_date, String note, int rating, String img_url) {
        this.fName = fName;
        this.brand = brand;
        this.manifac_date = manifac_date;
        this.ex_date = ex_date;
        this.open_date = open_date;
        this.note = note;
        this.rating = rating;
        this.img_url = img_url;
    }

    public static final Creator<ShelfItemList> CREATOR = new Creator<ShelfItemList>() {
        @Override
        public ShelfItemList createFromParcel(Parcel in) {
            return new ShelfItemList(in);
        }

        @Override
        public ShelfItemList[] newArray(int size) {
            return new ShelfItemList[size];
        }
    };

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManifac_date() {
        return manifac_date;
    }

    public void setManifac_date(String manifac_date) {
        this.manifac_date = manifac_date;
    }

    public String getEx_date() {
        return ex_date;
    }

    public void setEx_date(String ex_date) {
        this.ex_date = ex_date;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(fName);
        parcel.writeString(brand);
        parcel.writeString(manifac_date);
        parcel.writeString(ex_date);
        parcel.writeString(open_date);
        parcel.writeString(note);
        parcel.writeInt(rating);
        parcel.writeString(img_url);
    }


}
