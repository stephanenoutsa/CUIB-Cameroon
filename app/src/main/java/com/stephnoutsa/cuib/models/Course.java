package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 11/22/16.
 */

public class Course {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("code")
    String code;

    @SerializedName("name")
    String name;

    @SerializedName("schools")
    String[] schools;

    @SerializedName("departments")
    String[] departments;

    @SerializedName("levels")
    String[] levels;

    // Empty constructor
    public Course() {

    }

    // Constructor
    public Course(int id, String code, String name, String[] schools, String[] departments, String[] levels) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.schools = schools;
        this.departments = departments;
        this.levels = levels;
    }

    // Constructor
    public Course(String code, String name, String[] schools, String[] departments, String[] levels) {
        this.code = code;
        this.name = name;
        this.schools = schools;
        this.departments = departments;
        this.levels = levels;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSchools() {
        return schools;
    }

    public void setSchools(String[] schools) {
        this.schools = schools;
    }

    public String[] getDepartments() {
        return departments;
    }

    public void setDepartments(String[] departments) {
        this.departments = departments;
    }

    public String[] getLevels() {
        return levels;
    }

    public void setLevels(String[] levels) {
        this.levels = levels;
    }
}
