package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.library.services.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class GenereShellUIImpl implements GenereShellUI {
    private final GenreService genreService;

    @Override
    @ShellMethod("Add genre")
    public void addGenre(@ShellOption String name) {
        genreService.add(name);
    }

    @Override
    @ShellMethod("Print all genres")
    public void printAllGenres() {
        genreService.getAll().forEach(System.out::println);
    }

    @Override
    @ShellMethod("Print genre by id")
    public void printGenres(@ShellOption String id) {
        System.out.println(genreService.getById(id));
    }

    @Override
    @ShellMethod("Update genre by id")
    public void updateGenre(@ShellOption String id, @ShellOption String name) {
        genreService.update(id, name);
    }

    @Override
    @ShellMethod("Delete gemre by id")
    public void deleteGenre(@ShellOption String id) {
        genreService.delete(id);
    }
}
