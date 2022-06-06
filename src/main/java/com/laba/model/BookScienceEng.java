package com.laba.model;

import com.laba.model.enums.Level;

public class BookScienceEng extends BookScience {

    private Level level;
    private String university;

    public BookScienceEng() {
    }

    public Level getLevel() {
        return level;
    }

    public BookScienceEng setLevel(Level level) {
        this.level = level;
        return this;
    }

    public String getUniversity() {
        return university;
    }

    public BookScienceEng setUniversity(String university) {
        this.university = university;
        return this;
    }
}
