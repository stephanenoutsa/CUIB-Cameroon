package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 10/11/16.
 */
public class User {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("dob")
    String dob;

    @SerializedName("gender")
    String gender;

    @SerializedName("password")
    String password;

    // Empty constructor
    public User() {
    }

    // Constructor
    public User(int id, String email, String phone, String dob, String gender, String password) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.password = password;
    }

    // Constructor
    public User(String email, String phone, String dob, String gender, String password) {
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.password = password;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
