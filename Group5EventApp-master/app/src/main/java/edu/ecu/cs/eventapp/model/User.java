package edu.ecu.cs.eventapp.model;

import java.io.Serializable;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by dev on 11/5/17.
 */

public class User implements Serializable {
    private String mUserID;
    private String mUsername;
    private String mEmail;
    private String mpassword;
    private String mphoneNumber;
    private String photoUri;
    String userEventID;
    public User(){

    }

public User(String userID){
    this.mUserID=userID;
}
    public String getmUserID() {
        return mUserID;
    }

    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getUserEventID() {
        return userEventID;
    }

    public void setUserEventID(String userEventID) {
        this.userEventID = userEventID;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getMpassword() {
        return mpassword;
    }

    public void setMpassword(String mpassword) {
        this.mpassword = mpassword;
    }

    public String getMphoneNumber() {
        return mphoneNumber;
    }

    public void setMphoneNumber(String mphoneNumber) {
        this.mphoneNumber = mphoneNumber;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
