package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.models.Genre;
import ru.otus.library.services.GenreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return genreService.getAll();
    }

    @GetMapping("/genre/{id}")
    public Genre getGenre(@PathVariable("id") String genreId) {
        return genreService.getById(genreId);
    }

    @PostMapping("/genre")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGenre(@RequestParam("name") String name) {
        genreService.add(name);
    }

    @PutMapping("/genre/{id}")
    public void editGenre(
            @PathVariable("id") String genreId,
            @RequestParam("name") String name
    ) {
        genreService.update(genreId, name);
    }

    @DeleteMapping("/genre/{id}")
    public void deleteGenre(@PathVariable("id") String genreId) {
        genreService.delete(genreId);
    }
}
