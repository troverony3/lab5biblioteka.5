package com.laba.model;

public class Teacher extends User {

    private String middleName;

    public Teacher() {
    }

    public String getMiddleName() {
        return middleName;
    }

    public Teacher setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }
}
