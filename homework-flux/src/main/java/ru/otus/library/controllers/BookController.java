package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.controllers.dto.BookDTO;
import ru.otus.library.models.Book;
import ru.otus.library.repositories.BookRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/books")
    public Flux<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/book/{id}")
    public Mono<Book> getBook(@PathVariable("id") String bookId) {
        return bookRepository.findById(bookId);
    }

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(
            @RequestBody BookDTO bookData
    ) {
        bookRepository
                .save(new Book(
                        ObjectId.get().toString(),
                        bookData.getName(),
                        bookData.getAuthors(),
                        bookData.getGenres(),
                        List.of()
                ))
                .subscribe();
    }

    @PutMapping("/book/{id}")
    public void editBook(
            @PathVariable("id") String bookId,
            @RequestBody BookDTO bookData
    ) {
        bookRepository.findById(bookId)
                .map(book -> {
                    book.setName(bookData.getName());
                    book.setAuthorList(bookData.getAuthors());
                    book.setGenreList(bookData.getGenres());
                    return book;
                })
                .subscribe(book -> bookRepository.save(book).subscribe());
    }

    @DeleteMapping("/book/{id}")
    public void deleteBook(
            @PathVariable("id") String bookId
    ) {
        bookRepository.deleteById(bookId).subscribe();
    }
}
