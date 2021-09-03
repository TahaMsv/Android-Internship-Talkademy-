package com.example.myimplicitintent.Models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class ContactModel implements Serializable {//todo ***** Serializable

    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";
    public static final String KEY_MOBILE_NUMBER = "mobile_number";

    private String id;
    private String name;
    private String mobileNumber;
    private Bitmap photo;

    public ContactModel() {
        this.id ="" ;
        this.name =  "";
        this.mobileNumber ="" ;
        this.photo=null;
    }
    public ContactModel(String id, String name, String mobileNumber, Bitmap photo) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.photo=photo;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}