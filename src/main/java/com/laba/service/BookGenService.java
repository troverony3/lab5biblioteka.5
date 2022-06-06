package com.laba.service;

import com.laba.model.*;
import com.laba.model.enums.Language;
import com.laba.model.enums.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BookGenService {

    private BookGenService() {
    }

    public static void fillAuthors(List<Book> books, List<String> authors) {
        books.forEach(book -> book.setAuthor(authors.get(ThreadLocalRandom.current().nextInt(authors.size()))));
    }

    public static void fillEnScienceBooksData(List<Book> books, List<String> subjects, List<String> universities, List<String> authors) {
        books.forEach(book -> {
            book.setAuthor(authors.get(ThreadLocalRandom.current().nextInt(authors.size())));
            ((BookScience) book).setSubject(subjects.get(ThreadLocalRandom.current().nextInt(subjects.size())));
            ((BookScienceEng) book).setLevel(Level.values()[ThreadLocalRandom.current().nextInt(Level.values().length)]);
            ((BookScienceEng) book).setUniversity(universities.get(ThreadLocalRandom.current().nextInt(universities.size())));
        });
    }

    public static void fillRuScienceBooksData(List<Book> books, List<String> subjects, List<String> types) {
        books.forEach(book -> {
            ((BookScience) book).setSubject(subjects.get(ThreadLocalRandom.current().nextInt(subjects.size())));
            ((BookScienceRus) book).setType(types.get(ThreadLocalRandom.current().nextInt(types.size())));
        });
    }

    public static List<Book> createBooksSciEn(List<String> titles) {
        List<Book> books = new ArrayList<>();
        titles.forEach(title -> books.add(new BookScienceEng().setLanguage(Language.EN).setTitle(title)));
        return books;
    }

    public static List<Book> createBooksSciRu(List<String> titles) {
        List<Book> books = new ArrayList<>();
        titles.forEach(title -> books.add(new BookScienceRus().setLanguage(Language.RU).setTitle(title)));
        return books;
    }

    public static List<Book> createBooksFic(List<String> titles, Language language) {
        List<Book> books = new ArrayList<>();
        titles.forEach(title -> books.add(new BookFiction().setLanguage(language).setTitle(title)));
        return books;
    }
}
