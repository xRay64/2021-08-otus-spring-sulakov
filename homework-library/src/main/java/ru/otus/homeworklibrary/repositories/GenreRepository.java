package ru.otus.homeworklibrary.repositories;

import ru.otus.homeworklibrary.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long count();

    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    List<Genre> findByName(String name);

    void updateNameById(long id, String name);

    void deleteById(long id);
}
