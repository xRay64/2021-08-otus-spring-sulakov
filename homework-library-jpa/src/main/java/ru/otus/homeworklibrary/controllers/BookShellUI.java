package ru.otus.homeworklibrary.controllers;

public interface BookShellUI {
    void printBook(long id);

    void printAllBooks();

    void addBook(String name);

    void updateBook(long id, String name);

    void deleteBook(long id);
}
