package ru.otus.homeworklibrary.dao;

import ru.otus.homeworklibrary.domain.Book;

import java.util.List;

public interface BookDAO {
    long count();

    Book getById(long id);

    List<Book> getAll();

    void add(Book author);

    void update(Book author);

    void delete(Book author);

    void deleteById(long id);
}
