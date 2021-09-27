package ru.otus.homeworklibrary.service;

import ru.otus.homeworklibrary.domain.Genre;

import java.util.List;

public interface GenresService {

    List<Genre> getAllGenres();

    Genre getGenre(long id);

    void updateGenre(long id, String name);

    long addGenre(String name);

    void deleteGenre(long id);

}
