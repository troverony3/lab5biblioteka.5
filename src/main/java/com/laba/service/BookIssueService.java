package com.laba.service;

import com.laba.model.Book;
import com.laba.model.User;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BookIssueService {

    // границы для распределения книг
    private static final int MIN_NUMBER_OF_BOOKS_INCLUDED = 3;
    private static final int MAX_NUMBER_OF_BOOKS_EXCLUDED = 11;

    public static void issueBooks(List<User> users, List<Book> books) {
        users.forEach(user -> {
            Collections.shuffle(books);
            int toIndex = MIN_NUMBER_OF_BOOKS_INCLUDED + ThreadLocalRandom.current().nextInt(MAX_NUMBER_OF_BOOKS_EXCLUDED - MIN_NUMBER_OF_BOOKS_INCLUDED);
            user.getBooks().addAll(books.subList(0, toIndex));
        });
    }
}
