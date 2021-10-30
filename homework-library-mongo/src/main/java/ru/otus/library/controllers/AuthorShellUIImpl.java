package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.services.AuthorService;

@ShellComponent
@RequiredArgsConstructor
public class AuthorShellUIImpl implements AuthorShellUI {

    private final AuthorService authorService;

    @Override
    @ShellMethod("Add author")
    public void addAuthor(@ShellOption String name) {
        authorService.add(name);
    }

    @Override
    @ShellMethod("Print all authors")
    public void printAllAuthors() {
        authorService.getAll().forEach(System.out::println);
    }

    @Override
    @ShellMethod("Print author by id")
    public void printAuthor(@ShellOption String id) {
        System.out.println(authorService.getById(id));
    }

    @Override
    @ShellMethod("Update author by id")
    public void updateAuthor(@ShellOption String id, @ShellOption String name) {
        authorService.update(id, name);
    }

    @Override
    @ShellMethod("Delete author by id")
    public void deleteAuthor(@ShellOption String id) {
        authorService.delete(id);
    }
}
