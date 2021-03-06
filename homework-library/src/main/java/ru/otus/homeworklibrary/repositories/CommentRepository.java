package ru.otus.homeworklibrary.repositories;

import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.BookComment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    long count();

    BookComment save(BookComment comment);

    Optional<BookComment> findById(long id);

    List<BookComment> findByBook(Book book);

    List<BookComment> findByBooks(List<Book> books);

    List<BookComment> findAll();

    void updateById(long id, String text);

    void update(BookComment comment);

    void delete(long id);
}
