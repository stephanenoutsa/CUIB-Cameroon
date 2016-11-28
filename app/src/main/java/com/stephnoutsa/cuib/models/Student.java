package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 10/10/16.
 */
public class Student {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("matricule")
    String matricule;

    @SerializedName("enrolled")
    String enrolYr;

    @SerializedName("level")
    String level;

    @SerializedName("school")
    String school;

    @SerializedName("department")
    String department;

    @SerializedName("email")
    String email;

    @SerializedName("phone")
    String phone;

    @SerializedName("password")
    String password;

    // Empty constructor
    public Student() {

    }

    // Constructor
    public Student(int id, String name, String matricule, String enrolYr, String level, String school,
                   String department, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.matricule = matricule;
        this.enrolYr = enrolYr;
        this.level = level;
        this.school = school;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Constructor
    public Student(String name, String matricule, String enrolYr, String level, String school,
                   String department, String email, String phone, String password) {
        this.name = name;
        this.matricule = matricule;
        this.enrolYr = enrolYr;
        this.level = level;
        this.school = school;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // Getter and Setter methods
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

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getEnrolYr() {
        return enrolYr;
    }

    public void setEnrolYr(String enrolYr) {
        this.enrolYr = enrolYr;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
