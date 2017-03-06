package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 11/17/16.
 */

public class Timetable {

    // Private variables
    int id;
    String school, department, level, url;

    // Empty constructor
    public Timetable() {

    }

    // Constructor
    public Timetable(int id, String school, String department, String level, String url) {
        this.id = id;
        this.school = school;
        this.department = department;
        this.level = level;
        this.url = url;
    }

    // Constructor
    public Timetable(String school, String department, String level, String url) {
        this.school = school;
        this.department = department;
        this.level = level;
        this.url = url;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
