package ru.otus.homeworklibrary.service;

import ru.otus.homeworklibrary.domain.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthor(long id);

    void updateAuthor(long id, String name);

    long addAuthor(String name);

    void deleteAuthor(long id);

}
