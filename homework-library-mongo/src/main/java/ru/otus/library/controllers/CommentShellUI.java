package ru.otus.library.controllers;

public interface CommentShellUI {
    void printAllComments();

    void printBookComments(String bookId);

    void addComment(String bookId, String text);

    void updateComment(String commentId, String newCommentText);

    void deleteComment(String commentId);
}
