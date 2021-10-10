package ru.otus.homeworklibrary.controllers;

public interface GenresShellUI {

    void printGenre(long id);

    void printAllGenres();

    void addGenre(String name);

//    void updateGenre(long id, String name);

    void deleteGenre(long id);
}
