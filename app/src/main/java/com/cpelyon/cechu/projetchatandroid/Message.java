package com.cpelyon.cechu.projetchatandroid;

import com.google.firebase.Timestamp;

public class Message {

    private String message;
    private Timestamp dateCreated;
    private String userSender;
    public Message() { }

    public Message(String message, String userSender) {
        this.message = message;
        this.userSender = userSender;
    }

    public Message(String message, String userSender,Timestamp timestamp) {
        this.message = message;
        this.userSender = userSender;
        this.dateCreated=timestamp;
    }

    // --- GETTERS ---
    public String getMessage() { return message; }
    public Timestamp getDateCreated() { return dateCreated; }
    public String getUserSender() { return userSender; }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(Timestamp dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(String userSender) { this.userSender = userSender; }
}
