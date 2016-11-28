package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 11/17/16.
 */

public class Department {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("school")
    String school;

    @SerializedName("level")
    String level;

    @SerializedName("timetable")
    String timetable;

    // Empty constructor
    public Department() {

    }

    // Constructor
    public Department(int id, String name, String school, String level, String timetable) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.level = level;
        this.timetable = timetable;
    }

    // Constructor
    public Department(String name, String school, String level, String timetable) {
        this.name = name;
        this.school = school;
        this.level = level;
        this.timetable = timetable;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }
}
