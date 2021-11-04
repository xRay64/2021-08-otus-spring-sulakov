package ru.otus.library.services;

import ru.otus.library.models.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(String bookId);

    void add(String name);

    void update(String id, String name);

    void deleteById(String bookId);

    long count();
}
