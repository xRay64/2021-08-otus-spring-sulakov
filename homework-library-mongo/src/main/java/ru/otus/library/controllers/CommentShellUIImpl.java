package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.models.Book;
import ru.otus.library.services.BookService;
import ru.otus.library.services.CommentService;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellUIImpl implements CommentShellUI {

    private final CommentService commentService;
    private final BookService bookService;

    @Override
    @ShellMethod("Print all comments")
    public void printAllComments() {
        commentService.getAllComments().forEach(System.out::println);
    }

    @Override
    @ShellMethod("Print comments by book Id")
    public void printBookComments(@ShellOption String bookId) {
        commentService.getCommentsByBook(bookService.getById(bookId)).forEach(System.out::println);
    }

    @Override
    @ShellMethod("Add book comment. Provide book Id as first param and comment text as second param")
    public void addComment(@ShellOption String bookId, @ShellOption String text) {
        Book book = bookService.getById(bookId);
        commentService.add(book, text);
    }

    @Override
    @ShellMethod("Update book comment. Provide book Id as first param and comment text as second param")
    public void updateComment(@ShellOption String commentId, @ShellOption String newCommentText) {
        commentService.update(commentId, newCommentText);
    }

    @Override
    @ShellMethod("Delete comment by Id")
    public void deleteComment(@ShellOption String commentId) {
        commentService.delete(commentId);
    }
}
