package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 1/26/17.
 */

public class Payment {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("date")
    String date;

    @SerializedName("amount")
    String amount;

    @SerializedName("type")
    String type;

    @SerializedName("school")
    String school;

    // Empty constructor
    public Payment() {

    }

    // Constructor
    public Payment(int id, String date, String amount, String type, String school) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.school = school;
    }

    // Constructor
    public Payment(String date, String amount, String type, String school) {
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.school = school;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

}
