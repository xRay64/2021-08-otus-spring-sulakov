package ru.otus.homeworklibrary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.services.BookService;
import ru.otus.homeworklibrary.services.CommentService;

@ShellComponent()
@AllArgsConstructor
public class CommentsShellUIImpl implements CommentsShellUI {

    private final CommentService commentService;
    private final BookService bookService;

    @Override
    @ShellMethod("Print comment by its id")
    public void printComment(@ShellOption long id) {
        System.out.println(commentService.getComment(id));
    }

    @Override
    @ShellMethod("Print comments by book id")
    public void printBookComments(@ShellOption long book_id) {
        Book book = bookService.get(book_id);
        commentService.getByBook(book).forEach(System.out::println);
    }

    @Override
    @ShellMethod("Print all comments")
    public void printAllComments() {
        commentService.getAllComments().forEach(System.out::println);
    }

    @Override
    @ShellMethod("Add new comment in comment list. Enter book id first, than enter a comment text.")
    public void addComment(@ShellOption long bookId, @ShellOption String name) {
        commentService.addComment(name, bookId);
    }

    @Override
    @ShellMethod("Delete comment from comment list by id")
    public void deleteComment(@ShellOption long id) {
        commentService.deleteCommentById(id);
    }
}
