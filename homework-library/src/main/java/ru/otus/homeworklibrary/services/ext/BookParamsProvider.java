package ru.otus.homeworklibrary.services.ext;

import ru.otus.homeworklibrary.models.Author;
import ru.otus.homeworklibrary.models.Genre;
import ru.otus.homeworklibrary.repositories.AuthorRepository;
import ru.otus.homeworklibrary.repositories.GenreRepository;

import java.util.List;

public interface BookParamsProvider {

    Author getAuthorFromUser(AuthorRepository repository);

    List<Genre> getGenreListFromUser(GenreRepository repository);
}
