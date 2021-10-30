package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
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
    public void add(String name) {
        List<Author> authorList = List.of(bookParamsProvider.getAuthorFromUser());
        List<Genre> genreList = bookParamsProvider.getGenreListFromUser();
        List<Comment> commentList = bookParamsProvider.getCommentsFromUser();
        Book newBook = new Book(bookParamsProvider.getNewBookId(), name, authorList, genreList, commentList);
        bookRepository.save(newBook);
    }

    @Override
    public void update(String id, String name) {
        Book updatedBook = bookRepository.findById(id).orElseThrow(RuntimeException::new);
        List<Author> authorList = List.of(bookParamsProvider.getAuthorFromUser());
        List<Genre> genreList = bookParamsProvider.getGenreListFromUser();
        List<Comment> commentList = bookParamsProvider.getCommentsFromUser();
        updatedBook.setName(name);
        updatedBook.setAuthorList(authorList);
        updatedBook.setGenreList(genreList);
        updatedBook.setCommentList(commentList);
        bookRepository.save(updatedBook);
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
