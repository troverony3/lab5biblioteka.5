package com.laba.model.enums;

public enum Level {
    BACHELOR("Бакалавр"),
    MASTER("Магистр");

    private final String levelName;

    Level(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }
}
