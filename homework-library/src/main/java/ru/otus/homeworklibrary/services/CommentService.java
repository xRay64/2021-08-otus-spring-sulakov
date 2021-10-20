package ru.otus.homeworklibrary.services;

import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.BookComment;

import java.util.List;

public interface CommentService {
    long addComment(String comment_text, long book_id);

    List<BookComment> getAllComments();

    BookComment getComment(long id);

    List<BookComment> getByBook(Book book);

    void updateComment(long id, String comment_text);

    void deleteCommentById(long id);
}
