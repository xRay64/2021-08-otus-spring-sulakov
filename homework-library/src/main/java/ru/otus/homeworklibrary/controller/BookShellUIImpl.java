package ru.otus.homeworklibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homeworklibrary.domain.Book;
import ru.otus.homeworklibrary.service.BookService;

@ShellComponent
@AllArgsConstructor
public class BookShellUIImpl implements BookShellUI{

    private final BookService bookService;

    @Override
    @ShellMethod("print book by id")
    public void printBook(long id) {
        System.out.println(bookService.get(id));
    }

    @Override
    @ShellMethod("print all books")
    public void printAllBooks() {
        for (Book book : bookService.getAll()) {
            System.out.println(book);
        }
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
