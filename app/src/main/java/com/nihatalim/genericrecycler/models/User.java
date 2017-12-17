package com.nihatalim.genericrecycler.models;

import java.util.Date;

/**
 * Created by thecower on 12/17/17.
 */

public class User {
    private String name;
    private Date date;

    public User() {
        this.date = new Date();
    }

    public User(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
