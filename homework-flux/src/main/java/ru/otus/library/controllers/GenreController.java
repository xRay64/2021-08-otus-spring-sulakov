package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.GenreRepository;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreRepository genreRepository;

    @GetMapping("/genres")
    public Flux<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/genre/{id}")
    public Mono<Genre> getGenre(@PathVariable("id") String genreId) {
        return genreRepository.findById(genreId);
    }

    @PostMapping("/genre")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGenre(@RequestParam("name") String name) {
        genreRepository.save(new Genre(ObjectId.get().toString(), name)).subscribe();
    }

    @PutMapping("/genre/{id}")
    public void editGenre(
            @PathVariable("id") String genreId,
            @RequestParam("name") String name
    ) {
        genreRepository.findById(genreId)
                .map(genre -> {
                    genre.setName(name);
                    return genre;
                })
                .subscribe(genre -> genreRepository.save(genre).subscribe());
    }

    @DeleteMapping("/genre/{id}")
    public void deleteGenre(@PathVariable("id") String genreId) {
        genreRepository.deleteById(genreId).subscribe();
    }
}
