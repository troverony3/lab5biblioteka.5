package com.laba.model.enums;

public enum Language {

    RU("русский"),
    EN("английский");

    private final String langName;

    Language(final String langName) {
        this.langName = langName;
    }

    public String getLangName() {
        return langName;
    }
}
