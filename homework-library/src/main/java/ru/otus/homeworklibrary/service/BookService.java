package ru.otus.homeworklibrary.service;

import ru.otus.homeworklibrary.domain.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book get(long id);

    long add(String name);

    void update(long id, String name);

    void delete(long id);
}
