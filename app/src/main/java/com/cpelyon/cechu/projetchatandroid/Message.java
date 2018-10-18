package com.cpelyon.cechu.projetchatandroid;

public class Message {

    private String message;
    private String dateCreated;
    private String userSender;
    public Message() { }

    public Message(String message, String userSender) {
        this.message = message;
        this.userSender = userSender;
    }

    public Message(String message, String userSender,String timestamp) {
        this.message = message;
        this.userSender = userSender;
        this.dateCreated=timestamp;
    }

    // --- GETTERS ---
    public String getMessage() { return message; }
    public String getDateCreated() { return dateCreated; }
    public String getUserSender() { return userSender; }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(String userSender) { this.userSender = userSender; }
}
