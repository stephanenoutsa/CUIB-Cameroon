package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 11/26/16.
 */

public class Token {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("value")
    String value;

    @SerializedName("school")
    String school;

    @SerializedName("department")
    String department;

    @SerializedName("level")
    String level;

    // Empty constructor
    public Token() {

    }

    // Constructor
    public Token(int id, String value, String school, String department, String level) {
        this.id = id;
        this.value = value;
        this.school = school;
        this.department = department;
        this.level = level;
    }

    // Constructor
    public Token(String value, String school, String department, String level) {
        this.value = value;
        this.school = school;
        this.department = department;
        this.level = level;
    }

    // Getter and Setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
