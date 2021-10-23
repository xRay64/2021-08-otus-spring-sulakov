package ru.otus.homeworklibrary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homeworklibrary.models.Author;
import ru.otus.homeworklibrary.services.AuthorService;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class AuthorsShellUIImpl implements AuthorsShellUI {

    private final AuthorService authorService;

    @Override
    @ShellMethod(key = "print-author", value = "Printing all authors")
    public void printAuthor(@ShellOption long authorId) {
        System.out.println(authorService.getAuthor(authorId));
    }

    @Override
    @ShellMethod(key = "print-all-authors", value = "Printing all authors")
    public void printAllAuthors() {
        List<Author> authorList = authorService.getAllAuthors();
        for (Author author : authorList) {
            System.out.println(author);
        }
    }

    @Override
    @ShellMethod("Add new author in authors list")
    public void addAuthor(@ShellOption String name) {
        authorService.addAuthor(name);
    }

    @Override
    @ShellMethod("Update author name by id")
    public void updateAuthor(@ShellOption long id, @ShellOption String name) {
        authorService.updateAuthor(id, name);
    }

    @Override
    @ShellMethod("Delete author from author list by id")
    public void deleteAuthor(@ShellOption long id) {
        authorService.deleteAuthor(id);
    }
}
