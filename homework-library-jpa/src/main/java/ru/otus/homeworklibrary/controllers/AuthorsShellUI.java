package ru.otus.homeworklibrary.controllers;

import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

public interface AuthorsShellUI {

    void printAuthor(long authorId);

    void printAllAuthors();

    void addAuthor(String name);

    void updateAuthor(@ShellOption long id, @ShellOption String name);

    void deleteAuthor(long id);
}
