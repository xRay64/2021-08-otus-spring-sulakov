package ru.otus.homeworklibrary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homeworklibrary.services.BookService;

@ShellComponent
@AllArgsConstructor
public class BookShellUIImpl implements BookShellUI{

    private final BookService bookService;

    @Override
    @ShellMethod("print book by id")
    public void printBook(long id) {
        System.out.println(bookService.getWithComments(id));
    }

    @Override
    @ShellMethod("print all books")
    public void printAllBooks() {
        bookService.getAllWithComment().forEach(System.out::println);
    }

    @Override
    @ShellMethod("add a book to database")
    public void addBook(@ShellOption String name) {
        long newBookId = bookService.add(name);
        System.out.println("Книга добавлена с ID: " + newBookId);
    }

    @Override
    @ShellMethod("update book in database")
    public void updateBook(@ShellOption long id, @ShellOption String name) {
        bookService.update(id, name);
        System.out.println("Книга с ID " + id + " обновлена");
    }

    @Override
    @ShellMethod("delete book by id")
    public void deleteBook(@ShellOption long id) {
        bookService.delete(id);
    }
}
