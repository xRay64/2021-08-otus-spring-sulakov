package ru.otus.homeworklibrary.controllers;

public interface CommentsShellUI {
    void printComment(long id);

    void printBookComments(long book_id);

    void printAllComments();

    void addComment(long bookId, String name);

    void deleteComment(long id);
}
