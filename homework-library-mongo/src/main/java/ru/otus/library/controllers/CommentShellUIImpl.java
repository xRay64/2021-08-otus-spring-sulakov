package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.services.CommentService;

@ShellComponent
@RequiredArgsConstructor
public class CommentShellUIImpl implements CommentShellUI {

    private final CommentService commentService;

    @Override
    @ShellMethod("Print books comment")
    public void printBookComments(@ShellOption String bookId) {
        commentService.getBookComments(bookId).forEach(System.out::println);
    }

    @Override
    @ShellMethod("Add comment for book")
    public void addBookComment(@ShellOption String bookId, @ShellOption String commentText) {
        commentService.addBookComment(bookId, commentText);
    }
}
