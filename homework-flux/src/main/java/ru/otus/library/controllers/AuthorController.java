package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.models.Author;
import ru.otus.library.repositories.AuthorRepository;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("/authors")
    public Flux<Author> getAuthors() {
        return authorRepository.findAll();
    }

    @GetMapping("/author/{id}")
    public Mono<Author> getAuthor(@PathVariable("id") String authorId) {
        return authorRepository.findById(authorId);
    }

    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    public void addAuthor(@RequestParam("name") String name) {
        saveAuthor(new Author(ObjectId.get().toString(), name));
    }

    @PutMapping("/author/{id}")
    public void editAuthor(
            @PathVariable("id") String authorId,
            @RequestParam("name") String name
    ) {
        authorRepository.findById(authorId)
                .map(author -> {
                    author.setName(name);
                    return author;
                })
                .subscribe(this::saveAuthor);
    }

    @DeleteMapping("/author/{id}")
    public void deleteAuthor(@PathVariable("id") String authorId) {
        authorRepository.deleteById(authorId).subscribe();
    }

    private void saveAuthor(Author author) {
        authorRepository.save(author).subscribe();
    }
}
