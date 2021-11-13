package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.models.Book;
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.BookService;
import ru.otus.library.services.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @GetMapping("/")
    public String getBooks(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "index";
    }

    @GetMapping("/book/add")
    public String addBookForm(Model model) {
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());
        return "bookAdd";
    }

    @PostMapping("/book/add")
    public String addBook(
            @RequestParam("book-name") String bookName,
            @RequestParam("authorId") List<String> authorIds,
            @RequestParam("genreId") List<String> genreIds
    ) {
        Book newBook = new Book(
                ObjectId.get().toString(),
                bookName,
                authorIds.stream().map(authorService::getById).collect(Collectors.toList()),
                genreIds.stream().map(genreService::getById).collect(Collectors.toList()),
                List.of()
        );
        bookService.save(newBook);
        return "redirect:/";
    }

    @GetMapping("/book/edit")
    public String editBookForm(@RequestParam("bookId") String bookId, Model model) {
        model.addAttribute("book", bookService.getById(bookId));
        model.addAttribute("authors", authorService.getAll());
        model.addAttribute("genres", genreService.getAll());
        return "bookEdit";
    }

    @PostMapping("/book/edit")
    public String editBook(
            @RequestParam("id") String bookId,
            @RequestParam("book-name") String bookName,
            @RequestParam("authorId") List<String> authorIds,
            @RequestParam("genreId") List<String> genreIds
    ) {
        Book editedBook = bookService.getById(bookId);
        editedBook.setName(bookName);
        editedBook.setAuthorList(authorIds.stream().map(authorService::getById).collect(Collectors.toList()));
        editedBook.setGenreList(genreIds.stream().map(genreService::getById).collect(Collectors.toList()));
        bookService.save(editedBook);
        return "redirect:/";
    }

    @PostMapping("/book/delete")
    public String deleteBook(
            @RequestParam("bookId") String bookId
    ) {
        bookService.deleteById(bookId);
        return "redirect:/";
    }
}
