package ru.otus.library.controllers;

public interface GenereShellUI {
    void addGenre(String name);

    void printAllGenres();

    void printGenres(String id);

    void updateGenre(String id, String name);

    void deleteGenre(String id);
}
