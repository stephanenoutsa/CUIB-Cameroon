package com.stephnoutsa.cuib.models;

/**
 * Created by stephnoutsa on 11/24/16.
 */

public class Message {

    // Private variables
    int id;
    String sender, time, title, body;

    // Empty constructor
    public Message() {

    }

    // Constructor
    public Message(int id, String sender, String time, String title, String body) {
        this.id = id;
        this.sender = sender;
        this.time = time;
        this.title = title;
        this.body = body;
    }

    // Constructor
    public Message(String sender, String time, String title, String body) {
        this.sender = sender;
        this.time = time;
        this.title = title;
        this.body = body;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
