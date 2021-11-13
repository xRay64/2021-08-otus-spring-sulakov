package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;
import ru.otus.library.repositories.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new NullPointerException("Book with id \"" + bookId + "\" doesn't exist"));
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
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
