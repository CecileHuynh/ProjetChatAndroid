package com.cpelyon.cechu.projetchatandroid;

import java.util.Date;

public class Message {

    private String message;
    private Date dateCreated;
    //private User userSender;
    private String userSender;
    //private String urlImage;

    public Message() { }

    public Message(String message, String userSender) {
        this.message = message;
        this.userSender = userSender;
    }

    public Message(String message, String urlImage, String userSender) {
        this.message = message;
        //this.urlImage = urlImage;
        this.userSender = userSender;
    }

    // --- GETTERS ---
    public String getMessage() { return message; }
    public Date getDateCreated() { return dateCreated; }
    public String getUserSender() { return userSender; }
    //public String getUrlImage() { return urlImage; }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(String userSender) { this.userSender = userSender; }
   // public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
}