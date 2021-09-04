package com.appintuitions.recycletoday.pojo;

import com.google.gson.annotations.SerializedName;

public class User {

    String uid;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("mobileNo")
    String mobileNo;

    @SerializedName("password")
    String password;

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
