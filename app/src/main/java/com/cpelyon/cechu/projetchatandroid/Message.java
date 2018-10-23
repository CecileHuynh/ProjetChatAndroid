package com.cpelyon.cechu.projetchatandroid;

public class Message {

    private String message;
    private String dateCreated;
    private String userSender;
    private String username;

    public Message() { }

    public Message(String message, String userSender) {
        this.message = message;
        this.userSender = userSender;
    }




    public Message(String message, String userSender, String timestamp, String username) {
        this.message = message;
        this.userSender = userSender;
        this.dateCreated=timestamp;
        this.username = username;
    }

    // --- GETTERS ---
    public String getMessage() { return message; }
    public String getDateCreated() { return dateCreated; }
    public String getUserSender() { return userSender; }
    public String getUsername() {return username; }


    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(String userSender) { this.userSender = userSender; }
    public void setUsername(String username) { this.username = username; }
}
