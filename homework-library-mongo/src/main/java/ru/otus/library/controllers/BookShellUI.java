package ru.otus.library.controllers;

public interface BookShellUI {
    void printBook(String id);

    void printAllBooks();

    void addBook(String name);

    void updateBook(String id, String newBookName);

    void deleteBook(String id);
}
