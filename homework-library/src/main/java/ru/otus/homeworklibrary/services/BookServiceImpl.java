package ru.otus.homeworklibrary.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homeworklibrary.models.Author;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.Genre;
import ru.otus.homeworklibrary.repositories.AuthorRepository;
import ru.otus.homeworklibrary.repositories.BookRepository;
import ru.otus.homeworklibrary.repositories.GenreRepository;
import ru.otus.homeworklibrary.services.ext.BookParamsProvider;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookParamsProvider bookParamsProvider;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book get(long id) {
        return bookRepository.find(id).orElseThrow(() -> new RuntimeException("Book id isn't exists"));
    }

    @Override
    @Transactional
    public long add(String name) {
        Book newBook = new Book(0, name, null, null);
        List<Author> bookAuthors = List.of(bookParamsProvider.getAuthorFromUser(authorRepository));
        List<Genre> bookGenres = bookParamsProvider.getGenreListFromUser(genreRepository);
        newBook.setAuthor(bookAuthors);
        newBook.setGenreList(bookGenres);
        Book savedBook = bookRepository.save(newBook);
        return savedBook.getId();
    }

    @Override
    @Transactional
    public void update(long id, String name) {
        Book updatingBook = bookRepository.find(id).get();
        ArrayList<Author> authorList = new ArrayList<>();
        authorList.add(bookParamsProvider.getAuthorFromUser(authorRepository));
        List<Genre> bookGenres = bookParamsProvider.getGenreListFromUser(genreRepository);
        updatingBook.setName(name);
        updatingBook.setAuthor(authorList);
        updatingBook.setGenreList(bookGenres);
        bookRepository.update(updatingBook);
    }

    @Override
    @Transactional
    public void delete(long id) {
        bookRepository.deleteById(id);
    }
}
