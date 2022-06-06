package com.laba.service;

import com.laba.model.Book;
import com.laba.model.Student;
import com.laba.model.Teacher;
import com.laba.model.User;
import com.laba.model.enums.Language;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// класс с шаблонными методами
public abstract class ExcelReaderService {

    // листы англоязычных научных книг
    private static final String EN_SCIENCE_BOOK_SHEET = "en_sc";
    private static final String EN_SUBJECT_SHEET = "en_уч";
    private static final String EN_UNIVERSITY_SHEET = "en_un";
    private static final String EN_SCIENCE_AUTHOR_SHEET = "en_au";

    // листы русскоязычных научных книг
    private static final String RU_SCIENCE_BOOK_SHEET = "ru_sc";
    private static final String RU_SUBJECT_SHEET = "ру_уч";
    private static final String RU_TYPE_SHEET = "ру_тип";

    // листы англоязычного фикшена
    private static final String EN_FICTION_BOOK_SHEET = "en_fic";
    private static final String EN_FICTION_AUTHOR_SHEET = "en_fic_au";

    // листы русскоязычного фикшена
    private static final String RU_FICTION_BOOK_SHEET = "ру_фик";
    private static final String RU_FICTION_AUTHOR_SHEET = "ру_авт";

    // листы пользователей
    private static final String MALE_NAME_SHEET = "name";
    private static final String FEMALE_NAME_SHEET = "w_name";
    private static final String STUDENT_SURNAME_SHEET = "surname";
    private static final String TEACHER_SURNAME_SHEET = "prof_surname";
    private static final String TEACHER_MIDDLENAME_SHEET = "middlename";

    public List<Book> readBooks(String filename) throws IOException {
        Workbook workbook = getWorkbook(filename);
        List<Book> allBooks = new ArrayList<>();
        allBooks.addAll(readFiction(workbook, Language.EN));
        allBooks.addAll(readFiction(workbook, Language.RU));
        allBooks.addAll(readScienceEn(workbook));
        allBooks.addAll(readScienceRu(workbook));
        return allBooks;
    }

    private List<Book> readScienceEn(Workbook workbook) {
        List<String> titles = getTitles(workbook, EN_SCIENCE_BOOK_SHEET);
        List<Book> books = new ArrayList<>(BookGenService.createBooksSciEn(titles));
        Sheet subjectsSheet = workbook.getSheet(EN_SUBJECT_SHEET);
        Sheet universitiesSheet = workbook.getSheet(EN_UNIVERSITY_SHEET);
        Sheet authorsSheet = workbook.getSheet(EN_SCIENCE_AUTHOR_SHEET);
        List<String> subjects = new ArrayList<>();
        List<String> universities = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        subjectsSheet.rowIterator().forEachRemaining(row -> subjects.add(row.getCell(0).getStringCellValue()));
        universitiesSheet.rowIterator().forEachRemaining(row -> universities.add(row.getCell(0).getStringCellValue()));
        authorsSheet.rowIterator().forEachRemaining(row -> authors.add(row.getCell(0).getStringCellValue()));
        BookGenService.fillEnScienceBooksData(books, subjects, universities, authors);
        return books;
    }

    private List<Book> readScienceRu(Workbook workbook) {
        List<String> titles = getTitles(workbook, RU_SCIENCE_BOOK_SHEET);
        List<Book> books = new ArrayList<>(BookGenService.createBooksSciRu(titles));
        Sheet subjectsSheet = workbook.getSheet(RU_SUBJECT_SHEET);
        Sheet typesSheet = workbook.getSheet(RU_TYPE_SHEET);
        List<String> subjects = new ArrayList<>();
        List<String> types = new ArrayList<>();
        subjectsSheet.rowIterator().forEachRemaining(row -> subjects.add(row.getCell(0).getStringCellValue()));
        typesSheet.rowIterator().forEachRemaining(row -> types.add(row.getCell(0).getStringCellValue()));
        BookGenService.fillRuScienceBooksData(books, subjects, types);
        return books;
    }

    private List<String> getTitles(Workbook workbook, String sheetName) {
        Sheet bookNamesSheet = workbook.getSheet(sheetName);
        List<String> titles = new ArrayList<>();
        bookNamesSheet.rowIterator()
                .forEachRemaining(row -> titles.add(row.getCell(0).getStringCellValue()));
        return titles;
    }

    private List<Book> readFiction(Workbook workbook, Language language) {
        List<String> titles = getTitles(workbook, language.equals(Language.EN) ? EN_FICTION_BOOK_SHEET : RU_FICTION_BOOK_SHEET);
        List<Book> books = BookGenService.createBooksFic(titles, language);
        Sheet authorsSheet = workbook.getSheet(language.equals(Language.EN) ? EN_FICTION_AUTHOR_SHEET : RU_FICTION_AUTHOR_SHEET);
        List<String> authors = new ArrayList<>();
        authorsSheet.rowIterator()
                .forEachRemaining(row -> authors.add(row.getCell(0).getStringCellValue()));
        BookGenService.fillAuthors(books, authors);
        return books;
    }

    public List<User> readUsers(String filename) throws IOException {
        Workbook workbook = getWorkbook(filename);
        List<User> users = new ArrayList<>();
        users.addAll(readStudents(workbook));
        users.addAll(readTeachers(workbook));
        return users;
    }

    private List<User> readStudents(Workbook workbook) {
        return readDefaultUsers(workbook, STUDENT_SURNAME_SHEET, Student.class);
    }

    private List<User> readTeachers(Workbook workbook) {
        List<User> users = readDefaultUsers(workbook, TEACHER_SURNAME_SHEET, Teacher.class);
        addMiddleNames(workbook, users);
        return users;
    }

    private void addMiddleNames(Workbook workbook, List<User> users) {
        Sheet middlenamesSheet = workbook.getSheet(TEACHER_MIDDLENAME_SHEET);
        List<String> middlenames = new ArrayList<>();
        middlenamesSheet.rowIterator()
                .forEachRemaining(row -> middlenames.add(row.getCell(0).getStringCellValue()));
        UserGenService.addMiddleNames(users, middlenames);
    }

    private List<User> readDefaultUsers(Workbook workbook, String surnameSheetName, Class<? extends User> userClass) {
        Sheet namesSheet = workbook.getSheet(MALE_NAME_SHEET);
        Sheet wNamesSheet = workbook.getSheet(FEMALE_NAME_SHEET);
        Sheet surnameSheet = workbook.getSheet(surnameSheetName);
        List<String> firstNamesMale = new ArrayList<>();
        List<String> firstNamesFemale = new ArrayList<>();
        List<String> surnames = new ArrayList<>();
        namesSheet.rowIterator()
                .forEachRemaining(row -> firstNamesMale.add(row.getCell(0).getStringCellValue()));
        wNamesSheet.rowIterator()
                .forEachRemaining(row -> firstNamesFemale.add(row.getCell(0).getStringCellValue()));
        surnameSheet.rowIterator().forEachRemaining(row -> surnames.add(row.getCell(0).getStringCellValue()));
        return UserGenService.createUsers(userClass, firstNamesMale, firstNamesFemale, surnames);
    }

    // шаблонный метод
    protected abstract Workbook getWorkbook(String filename) throws IOException;

}
