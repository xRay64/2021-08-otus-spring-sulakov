package ru.otus.homeworklibrary.repositories;

import ru.otus.homeworklibrary.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();

    Book save(Book book);

    Optional<Book> find(long id);

    List<Book> findAll();

    List<Book> findByName(String bookNmae);

    void update(Book book);

    void updateNameById(long id, String name);

    void deleteById(long id);
}
