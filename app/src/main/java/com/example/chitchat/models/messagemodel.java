package com.example.chitchat.models;

public class messagemodel {
    String uID ,message;
    Long timestampp;

    // constructors
    public messagemodel(String uID, String message) {
        this.uID = uID;
        this.message = message;
    }
    public messagemodel(String uID, String message, Long timestampp) {
        this.uID = uID;
        this.message = message;
        this.timestampp = timestampp;
    }
    public messagemodel(){

        // empty construcor for fire base
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestampp() {
        return timestampp;
    }

    public void setTimestampp(Long timestampp) {
        this.timestampp = timestampp;
    }
}
