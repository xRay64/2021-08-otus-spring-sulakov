package ru.otus.homeworklibrary.dao;

import ru.otus.homeworklibrary.domain.Genre;

import java.util.List;

public interface GenreDAO {
    long count();

    Genre getById(long id);

    List<Genre> getAll();

    long insert(Genre author);

    void update(Genre author);

    void delete(Genre author);

    void deleteById(long id);

    long getMaxId();
}
