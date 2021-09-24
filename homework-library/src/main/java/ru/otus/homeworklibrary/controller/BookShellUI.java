package ru.otus.homeworklibrary.controller;

import org.springframework.shell.standard.ShellOption;

public interface BookShellUI {
    void printBook(long id);

    void printAllBooks();

    void addBook(String name);

    void updateBook(long id, String name);

    void deleteBook(long id);
}
