package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.controllers.dto.BookDTO;
import ru.otus.library.models.Book;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAll();
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable("id") String bookId) {
        return bookService.getById(bookId);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(
            @RequestBody BookDTO bookData
    ) {
        Book newBook = new Book(
                ObjectId.get().toString(),
                bookData.getName(),
                bookData.getAuthors(),
                bookData.getGenres(),
                List.of()
        );
        bookService.save(newBook);
    }

    @PutMapping("/book/{id}")
    public void editBook(
            @PathVariable("id") String bookId,
            @RequestBody BookDTO bookData
    ) {
        Book editedBook = bookService.getById(bookId);
        editedBook.setName(bookData.getName());
        editedBook.setAuthorList(bookData.getAuthors());
        editedBook.setGenreList(bookData.getGenres());
        bookService.save(editedBook);
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(
            @PathVariable("id") String bookId
    ) {
        bookService.deleteById(bookId);
    }
}
