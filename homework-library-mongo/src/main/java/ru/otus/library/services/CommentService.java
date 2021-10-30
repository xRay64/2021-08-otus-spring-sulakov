package ru.otus.library.services;

import ru.otus.library.models.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getBookComments(String bookId);
    void addBookComment(String bookId, String commentText);
}
