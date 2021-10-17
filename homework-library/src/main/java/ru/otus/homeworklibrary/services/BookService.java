package ru.otus.homeworklibrary.services;

import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.services.dto.BookDto;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    List<BookDto> getAllWithComment();

    Book get(long id);

    BookDto getWithComments(long id);

    long add(String name);

    void update(long id, String name);

    void delete(long id);
}
