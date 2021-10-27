package ru.otus.library.repositories;

import ru.otus.library.models.Author;
import ru.otus.library.models.Genre;

import java.util.List;

public interface BookCustomRepository {
    List<Author> findAllAuthors();

    List<Genre> findAllGenres();
}
