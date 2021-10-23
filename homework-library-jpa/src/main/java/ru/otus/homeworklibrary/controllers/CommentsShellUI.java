package ru.otus.homeworklibrary.controllers;

public interface CommentsShellUI {
    void printComment(long id);

    void printBookComments(long book_id);

    void printAllComments();

    void addComment(long bookId, String name);

    void updateComment(long id, String commentText);

    void deleteComment(long id);
}
