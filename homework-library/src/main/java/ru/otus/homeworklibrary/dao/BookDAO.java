package ru.otus.homeworklibrary.dao;

import ru.otus.homeworklibrary.domain.Book;

import java.util.List;

public interface BookDAO {
    long count();

    Book getById(long id);

    List<Book> getAll();

    long add(Book book);

    void update(Book book);

    void delete(Book book);

    void deleteById(long id);

    long getMaxId();
}
