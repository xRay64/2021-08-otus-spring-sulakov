package ru.otus.homeworklibrary.services;

import ru.otus.homeworklibrary.models.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthor(long id);

    void updateAuthor(long id, String name);

    long addAuthor(String name);

    void deleteAuthor(long id);

}
