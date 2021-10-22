package ru.otus.homeworklibrary.controllers;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.homeworklibrary.models.Genre;
import ru.otus.homeworklibrary.services.GenresService;

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
    @ShellMethod("Update genre by id")
    public void updateGenre(@ShellOption long id, @ShellOption String name) {
        genresService.updateGenre(id, name);
    }

    @Override
    @ShellMethod("Delete genre from genres list by id")
    public void deleteGenre(@ShellOption long id) {
        genresService.deleteGenre(id);
    }
}
