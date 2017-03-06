package com.stephnoutsa.cuib.models;

/**
 * Created by stephnoutsa on 10/11/16.
 */
public class Subscribed {

    // Private variables
    int id;
    String status;

    // Empty constructor
    public Subscribed() {
    }

    // Constructor
    public Subscribed(int id, String status) {
        this.id = id;
        this.status = status;
    }

    // Constructor
    public Subscribed(String status) {
        this.status = status;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
