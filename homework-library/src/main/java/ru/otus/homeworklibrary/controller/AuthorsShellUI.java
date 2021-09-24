package ru.otus.homeworklibrary.controller;

public interface AuthorsShellUI {

    void printAuthor(long authorId);

    void printAllAuthors();

    void addAuthor(String name);

//    void updateAuthor(long id, String name);

    void deleteAuthor(long id);
}
