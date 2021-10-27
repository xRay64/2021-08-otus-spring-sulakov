package ru.otus.library.controllers;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.services.BookService;

@ShellComponent
@AllArgsConstructor
public class BookShellUIImpl implements BookShellUI {

    private final BookService bookService;

    @Override
    @ShellMethod("print book by id")
    public void printBook(String id) {
        System.out.println(bookService.getById(id));
    }

    @Override
    @ShellMethod("print all books")
    public void printAllBooks() {
        bookService.getAll().forEach(System.out::println);
    }

    @Override
    @ShellMethod("print all authors")
    public void printAllAuthors() {
        bookService.getAllAuthors().forEach(System.out::println);
    }

    @Override
    @ShellMethod("print all genres")
    public void printAllGenres() {
        bookService.getAllGenres().forEach(System.out::println);
    }

    @Override
    @ShellMethod("add a book to database")
    public void addBook(@ShellOption String name) {
        bookService.add(name);
        System.out.println("Книга добавлена");
    }

    /*@Override
    @ShellMethod("update book in database")
    public void updateBook(@ShellOption long id, @ShellOption String name) {
        bookService.update(id, name);
        System.out.println("Книга с ID " + id + " обновлена");
    }*/

    @Override
    @ShellMethod("delete book by id")
    public void deleteBook(@ShellOption String id) {
        bookService.deleteById(id);
    }
}
