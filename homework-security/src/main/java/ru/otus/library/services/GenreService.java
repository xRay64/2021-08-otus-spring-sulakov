package ru.otus.library.services;

import ru.otus.library.models.Genre;

import java.util.List;

public interface GenreService {
    void add(String name);

    List<Genre> getAll();

    Genre getById(String id);

    void update(String id, String name);

    void delete(String id);
}
