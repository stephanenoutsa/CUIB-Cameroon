package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 12/28/16.
 */

public class Results {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("year")
    String year;

    @SerializedName("semester")
    String semester;

    @SerializedName("matricule")
    String matricule;

    @SerializedName("url")
    String url;

    // Empty constructor
    public Results() {

    }

    // Constructor
    public Results(int id, String year, String semester, String matricule, String url) {
        this.id = id;
        this.year = year;
        this.semester = semester;
        this.matricule = matricule;
        this.url = url;
    }

    // Constructor
    public Results(String year, String semester, String matricule, String url) {
        this.year = year;
        this.semester = semester;
        this.matricule = matricule;
        this.url = url;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
