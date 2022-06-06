package com.laba.model;

public abstract class BookScience extends Book {

    private String subject; // дисциплина

    public BookScience() {
    }

    public String getSubject() {
        return subject;
    }

    public BookScience setSubject(String subject) {
        this.subject = subject;
        return this;
    }
}
