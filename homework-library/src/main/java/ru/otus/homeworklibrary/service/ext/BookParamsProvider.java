package ru.otus.homeworklibrary.service.ext;

import ru.otus.homeworklibrary.dao.AuthorDAO;
import ru.otus.homeworklibrary.dao.GenreDAO;
import ru.otus.homeworklibrary.domain.Author;
import ru.otus.homeworklibrary.domain.Genre;

import java.util.List;

public interface BookParamsProvider {

    Author getAuthorFromUser(AuthorDAO dao);

    List<Genre> getGenreListFromUser(GenreDAO dao);
}
