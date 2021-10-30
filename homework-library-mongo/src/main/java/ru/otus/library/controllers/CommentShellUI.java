package ru.otus.library.controllers;

public interface CommentShellUI {
    void printBookComments(String bookId);

    void addBookComment(String bookId, String commentText);
}
