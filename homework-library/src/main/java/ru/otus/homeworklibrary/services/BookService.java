package ru.otus.homeworklibrary.services;

import ru.otus.homeworklibrary.models.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book get(long id);

    long add(String name);

    void update(long id, String name);

    void delete(long id);
}
