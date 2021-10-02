package ru.otus.homeworklibrary.controllers;

public interface AuthorsShellUI {

    void printAuthor(long authorId);

    void printAllAuthors();

    void addAuthor(String name);

//    void updateAuthor(long id, String name);

    void deleteAuthor(long id);
}
