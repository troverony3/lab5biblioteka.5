package com.laba.gui;

import com.laba.model.*;
import com.laba.model.enums.Sex;
import com.laba.service.BookIssueService;
import com.laba.service.ExcelReaderService;
import com.laba.service.UserGenService;
import com.laba.service.XlsxReaderServiceImpl;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GUI extends JFrame {

    private final JFileChooser fileChooser = new JFileChooser();
    private final JTree jTree;

    private final List<User> users = new ArrayList<>();
    private List<Book> books;

    public GUI() {
        super("form");

        this.setBounds(300, 300, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new GridBagLayout());


        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Библиотека");
        jTree = new JTree(top);

        JButton importUsersButton = new JButton("Import users");
        importUsersButton.addActionListener(actionEvent -> {
            int returnValue = fileChooser.showOpenDialog(GUI.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ExcelReaderService readerService = XlsxReaderServiceImpl.of();
                try {
                    users.addAll(readerService.readUsers(selectedFile.getAbsolutePath()));
                    JOptionPane.showMessageDialog(null, "Done", "Import", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "файл не существует", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        JButton importBooksButton = new JButton("Import books");
        importBooksButton.addActionListener(actionEvent -> {
            int returnValue = fileChooser.showOpenDialog(GUI.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                ExcelReaderService readerService = XlsxReaderServiceImpl.of();
                try {
                    books = readerService.readBooks(selectedFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(null, "Done", "Import", JOptionPane.PLAIN_MESSAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "файл не существует", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        JButton buildTreeButton = new JButton("Show tree");
        buildTreeButton.addActionListener(actionEvent -> {
            if (users.size() > 0 && books != null) {
                BookIssueService.issueBooks(users, books);
                fillTree(top, users);
            } else {
                JOptionPane.showMessageDialog(null, "сначала нужно импортировать пользователей и книги", "Error", JOptionPane.PLAIN_MESSAGE);
            }
        });

        JButton addUserButton = new JButton("Add user");
        addUserButton.addActionListener(actionEvent -> {
            User userFromGUI;
            String userType = (String) JOptionPane.showInputDialog(null, "Choose user type", "", JOptionPane.PLAIN_MESSAGE, null, new String[]{"Student", "Teacher"}, "Student");
            Sex userSex = (Sex) JOptionPane.showInputDialog(null, "Choose user sex", "", JOptionPane.PLAIN_MESSAGE, null, Sex.values(), Sex.MALE);
            String firstName = JOptionPane.showInputDialog(null, "Enter first name", null, JOptionPane.PLAIN_MESSAGE);
            String lastName = JOptionPane.showInputDialog(null, "Enter last name", null, JOptionPane.PLAIN_MESSAGE);
            if (userType.equals("Teacher")) {
                String middleName = JOptionPane.showInputDialog(null, "Enter middle name", null, JOptionPane.PLAIN_MESSAGE);
                userFromGUI = UserGenService.createUserFromGUI(firstName, lastName, middleName, userSex, Teacher.class);
            } else {
                userFromGUI = UserGenService.createUserFromGUI(firstName, lastName, null, userSex, Student.class);
            }
            if (books != null) {
                BookIssueService.issueBooks(Collections.singletonList(userFromGUI), books);
            }
            users.add(userFromGUI);
            fillTree(top, users);
        });

        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.weightx = 0.5;
        buttonConstraints.fill = GridBagConstraints.HORIZONTAL;
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;

        GridBagConstraints treeConstraints = new GridBagConstraints();
        treeConstraints.fill = GridBagConstraints.HORIZONTAL;
        treeConstraints.anchor = GridBagConstraints.PAGE_END;
        treeConstraints.gridwidth = 4;
        treeConstraints.gridy = 1;

        contentPane.add(importUsersButton, buttonConstraints);
        buttonConstraints.gridx = 1;
        contentPane.add(importBooksButton, buttonConstraints);
        buttonConstraints.gridx = 2;
        contentPane.add(buildTreeButton, buttonConstraints);
        buttonConstraints.gridx = 3;
        contentPane.add(addUserButton, buttonConstraints);
        JScrollPane scrollPane = new JScrollPane(jTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scrollPane, treeConstraints);
    }

    private void fillTree(DefaultMutableTreeNode top, List<User> users) {
        DefaultTreeModel tr = (DefaultTreeModel) jTree.getModel();

        DefaultMutableTreeNode teachers = new DefaultMutableTreeNode("Преподаватели");
        DefaultMutableTreeNode students = new DefaultMutableTreeNode("Студенты");
        top.removeAllChildren();
        top.add(teachers);
        top.add(students);
        users.forEach(user -> {
            DefaultMutableTreeNode userNode;
            if (user instanceof Teacher) {
                userNode = new DefaultMutableTreeNode(user.getLastName() + " " + user.getFirstName() + " " + ((Teacher) user).getMiddleName());
                teachers.add(userNode);
            } else {
                userNode = new DefaultMutableTreeNode(user.getLastName() + " " + user.getFirstName());
                students.add(userNode);
            }
            user.getBooks().forEach(book -> {
                DefaultMutableTreeNode bookNode = new DefaultMutableTreeNode(book.getTitle());
                userNode.add(bookNode);
                List<String> bookProps = new ArrayList<>();
                bookProps.add("Язык: " + book.getLanguage().getLangName());
                if (book.getAuthor() != null) {
                    bookProps.add("Автор: " + book.getAuthor());
                }
                if (book instanceof BookScienceEng) {
                    bookProps.add("Уровень: " + ((BookScienceEng) book).getLevel().getLevelName());
                    bookProps.add("Университет: " + ((BookScienceEng) book).getUniversity());
                } else if (book instanceof BookScienceRus) {
                    bookProps.add("Тип: " + ((BookScienceRus) book).getType());
                }
                bookProps.forEach(prop -> bookNode.add(new DefaultMutableTreeNode(prop)));
            });
        });
        tr.reload();
    }

}
