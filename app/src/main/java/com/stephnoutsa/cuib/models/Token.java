package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 11/26/16.
 */

public class Token {

    // Private variables
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    // Empty constructor
    public Token() {

    }

    // Constructor
    public Token(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor
    public Token(String name) {
        this.name = name;
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
}
