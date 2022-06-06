package com.laba.model;

import com.laba.model.enums.Language;

public abstract class Book {

    private String title;
    private Language language;
    private String author;

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public Language getLanguage() {
        return language;
    }

    public Book setLanguage(Language language) {
        this.language = language;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }
}
