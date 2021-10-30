package ru.otus.library.controllers;

public interface AuthorShellUI {
    void addAuthor(String name);

    void printAllAuthors();

    void printAuthor(String id);

    void updateAuthor(String id, String name);

    void deleteAuthor(String id);
}
