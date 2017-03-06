package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 11/22/16.
 */

public class Lecturer {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("avatar")
    String avatar;

    @SerializedName("bio")
    String bio;

    @SerializedName("departments")
    String[] departments;

    @SerializedName("levels")
    String[] levels;

    // Empty constructor
    public Lecturer() {

    }

    // Constructor
    public Lecturer(int id, String name, String avatar, String bio, String[] departments, String[] levels) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.bio = bio;
        this.departments = departments;
        this.levels = levels;
    }

    // Constructor
    public Lecturer(String name, String avatar, String bio, String[] departments, String[] levels) {
        this.name = name;
        this.avatar = avatar;
        this.bio = bio;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
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
