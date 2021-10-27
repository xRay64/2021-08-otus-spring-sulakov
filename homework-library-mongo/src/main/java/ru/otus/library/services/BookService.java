package ru.otus.library.services;

import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getById(String bookId);

    List<Author> getAllAuthors();

    List<Genre> getAllGenres();

    void add(String name);

    void deleteById(String bookId);

    long count();
}
