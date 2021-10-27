package ru.otus.library.controllers;

public interface BookShellUI {
    void printBook(String id);

    void printAllBooks();

    void addBook(String name);

//    void updateBook(long id, String name);

    void printAllAuthors();

    void printAllGenres();

    void deleteBook(String id);
}
