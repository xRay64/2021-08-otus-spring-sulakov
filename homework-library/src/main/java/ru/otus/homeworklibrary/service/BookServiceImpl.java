package ru.otus.homeworklibrary.service;

import org.springframework.stereotype.Service;
import ru.otus.homeworklibrary.dao.AuthorDAO;
import ru.otus.homeworklibrary.dao.BookDAO;
import ru.otus.homeworklibrary.dao.GenreDAO;
import ru.otus.homeworklibrary.domain.Author;
import ru.otus.homeworklibrary.domain.Book;
import ru.otus.homeworklibrary.domain.Genre;
import ru.otus.homeworklibrary.service.ext.BookParamsProvider;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final GenreDAO genreDAO;
    private final BookParamsProvider bookParamsProvider;

    public BookServiceImpl(BookDAO bookDAO, AuthorDAO authorDAO, GenreDAO genreDAO, BookParamsProvider bookParamsProvider) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.genreDAO = genreDAO;
        this.bookParamsProvider = bookParamsProvider;
    }

    @Override
    public List<Book> getAll() {
        return bookDAO.getAll();
    }

    @Override
    public Book get(long id) {
        return bookDAO.getById(id);
    }

    @Override
    public long add(String name) {
        Author bookAuthor = bookParamsProvider.getAuthorFromUser(authorDAO);
        List<Genre> bookGenres = bookParamsProvider.getGenreListFromUser(genreDAO);
        return bookDAO.add(new Book(bookDAO.getMaxId() + 1, name, bookAuthor, bookGenres));
    }

    @Override
    public void update(long id, String name) {
        Author bookAuthor = bookParamsProvider.getAuthorFromUser(authorDAO);
        List<Genre> bookGenres = bookParamsProvider.getGenreListFromUser(genreDAO);
        bookDAO.update(new Book(id, name, bookAuthor, bookGenres));
    }

    @Override
    public void delete(long id) {
        bookDAO.deleteById(id);
    }
}
