package ru.otus.homeworklibrary.controllers;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

public interface GenresShellUI {

    void printGenre(long id);

    void printAllGenres();

    void addGenre(String name);

    void updateGenre(long id, String name);

    void deleteGenre(long id);
}
