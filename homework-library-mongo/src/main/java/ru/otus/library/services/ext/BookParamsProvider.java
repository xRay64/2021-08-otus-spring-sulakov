package ru.otus.library.services.ext;

import ru.otus.library.models.Author;
import ru.otus.library.models.Comment;
import ru.otus.library.models.Genre;

import java.util.List;

public interface BookParamsProvider {

    Author getAuthorFromUser();

    List<Genre> getGenreListFromUser();

    List<Comment> getCommentsFromUser();

    String getNewBookId();
}
