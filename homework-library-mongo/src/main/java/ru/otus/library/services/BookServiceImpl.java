package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.services.ext.BookParamsProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookParamsProvider bookParamsProvider;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book with id \"" + bookId + "\" doesn't exist"));
    }

    @Override
    public List<Author> getAllAuthors() {
        return bookRepository.findAllAuthors();
    }

    @Override
    public List<Genre> getAllGenres() {
        return bookRepository.findAllGenres();
    }

    @Override
    public void add(String name) {
        List<Author> authorList = List.of(bookParamsProvider.getAuthorFromUser());
        List<Genre> genreList = bookParamsProvider.getGenreListFromUser();
        Book newBook = new Book(bookParamsProvider.getNewBookId(), name, authorList, genreList);
        bookRepository.save(newBook);
    }

    @Override
    public void deleteById(String bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public long count() {
        return bookRepository.count();
    }
}
