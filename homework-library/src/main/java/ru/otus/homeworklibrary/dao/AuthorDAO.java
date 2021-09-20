package ru.otus.homeworklibrary.dao;

import ru.otus.homeworklibrary.domain.Author;

import java.util.List;

public interface AuthorDAO {
    long count();

    Author getById(long id);

    List<Author> getAll();

    long insert(Author author);

    void update(Author author);

    void delete(Author author);

    void deleteById(long id);
}
