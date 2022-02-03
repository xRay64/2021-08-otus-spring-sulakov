package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.models.Author;
import ru.otus.library.services.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public List<Author> getAuthors() {
        return authorService.getAll();
    }

    @GetMapping("/author/{id}")
    public Author getAuthor(@PathVariable("id") String authorId) {
        return authorService.getById(authorId);
    }

    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@RequestParam("name") String name) {
        authorService.add(name);
    }

    @PutMapping("/author/{id}")
    public void editAuthor(
            @PathVariable("id") String authorId,
            @RequestParam("name") String name
    ) {
        authorService.update(authorId, name);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable("id") String authorId) {
        authorService.delete(authorId);
    }
}
