package ru.otus.homeworklibrary.repositories;

import ru.otus.homeworklibrary.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    long count();

    Author save(Author author);

    Optional<Author> find(long id);

    List<Author> findAll();

    List<Author> findByName(String name);

    void updateNameById(long id, String name);

    void delteById(long id);

    long getMaxId();
}
