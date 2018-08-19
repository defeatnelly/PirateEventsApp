package edu.ecu.cs.eventapp.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by dev on 11/6/17.
 */

public class PiratesEvent {
        private UUID uid;

    public UUID getUid() {
        return uid;
    }

    private String eventName;
        private Date eventDate;
        private String organizerName;
        private String eventdiscription;
        private String location;
        private String thumbnail;

    public PiratesEvent (UUID id){
        this.uid=id;
        this.eventDate=new Date();

    }
    public PiratesEvent(){
        this(UUID.randomUUID());}

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getEventdiscription() {
        return eventdiscription;
    }

    public void setEventdiscription(String eventdiscription) {
        this.eventdiscription = eventdiscription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    private String userID;
        private String userEmail;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }





}
