package com.example.chitchat.models;

public class user {
    String username ,purl,usermail, userid, lastmessage,userpassword;

    public user(String username, String purl, String usermail, String userid, String lastmessage, String userpassword) {
        this.username = username;
        this.purl = purl;
        this.usermail = usermail;
        this.userid = userid;
        this.lastmessage = lastmessage;
        this.userpassword = userpassword;
    }
    // empty constructor
    public user(){}
    // for  signup constructor
    public user(String username, String usermail, String userpassword) {
        this.username = username;
        this.usermail = usermail;
        this.userpassword = userpassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
    }

    public String getUserid(String key) {
        return userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }
}
