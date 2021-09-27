package ru.otus.homeworklibrary.controller;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homeworklibrary.domain.Genre;
import ru.otus.homeworklibrary.service.GenresService;

@ShellComponent()
@AllArgsConstructor
public class GenresShellUIImpl implements GenresShellUI{

    private final GenresService genresService;

    @Override
    @ShellMethod("Print genre by id")
    public void printGenre(@ShellOption long id) {
        System.out.println(genresService.getGenre(id));
    }

    @Override
    @ShellMethod("Print all genres")
    public void printAllGenres() {
        for (Genre genre : genresService.getAllGenres()) {
            System.out.println(genre);
        }
    }

    @Override
    @ShellMethod("Add new genre in genres list")
    public void addGenre(@ShellOption String name) {
        genresService.addGenre(name);
    }

    @Override
    @ShellMethod("Delete genre from genres list by id")
    public void deleteGenre(@ShellOption long id) {
        genresService.deleteGenre(id);
    }
}
