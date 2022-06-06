package com.laba.model;

import com.laba.model.enums.Sex;

import java.util.ArrayList;
import java.util.List;

public abstract class User {

    private String firstName;
    private String lastName;
    private List<Book> books;
    private Sex sex;

    public User() {
        this.books = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<Book> getBooks() {
        return books;
    }

    public User setBooks(List<Book> books) {
        this.books = books;
        return this;
    }

    public Sex getSex() {
        return sex;
    }

    public User setSex(Sex sex) {
        this.sex = sex;
        return this;
    }
}
