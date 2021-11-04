package ru.otus.library.services;

import ru.otus.library.models.Author;

import java.util.List;

public interface AuthorService {
    void add(String name);

    List<Author> getAll();

    Author getById(String id);

    void update(String id, String name);

    void delete(String id);
}
