package com.appintuitions.recycletoday.pojo;

import static com.appintuitions.recycletoday.AppController.user;

import java.util.ArrayList;

public class Complaint {

    private long date;
    private String cid,uid,name,mobile,email,address,caddress,comment,status;
    private ArrayList<String> images;

    public Complaint() {
        cid = String.valueOf(System.currentTimeMillis());
        uid = user.getUid();
        status = "Pending Review";
        date = System.currentTimeMillis();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) { this.cid = cid; }

    public long getDate() {
        return date;
    }

    public void setDate(long date) { this.date = date; }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCaddress() {
        return caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
