package ru.otus.library.services;

import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();

    List<Comment> getCommentsByBook(Book book);

    void add(Book book, String commentText);

    void update(String commentId, String newCommentText);

    void delete(String commentId);
}
