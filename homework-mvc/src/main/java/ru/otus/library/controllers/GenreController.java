package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.services.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("/genres")
    public String getGenres(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "genreList";
    }

    @GetMapping("/genres/add")
    public String addGenreForm(Model model) {
        model.addAttribute("type", "genres");
        return "nameAdd";
    }

    @PostMapping("/genres/add")
    public String addGenre(@RequestParam("name") String name) {
        genreService.add(name);
        return "redirect:/genres";
    }

    @GetMapping("/genres/edit")
    public String getGenreEditForm(Model model, @RequestParam("genreId") String genreId) {
        model.addAttribute("editedObject", genreService.getById(genreId));
        model.addAttribute("type", "genres");
        return "nameEdit";
    }

    @PostMapping("/genres/edit")
    public String editGenre(
            @RequestParam("id") String genreId,
            @RequestParam("name") String name
    ) {
        genreService.update(genreId, name);
        return "redirect:/genres";
    }

    @PostMapping("/genres/delete")
    public String deleteGenre(@RequestParam("id") String genreId) {
        genreService.delete(genreId);
        return "redirect:/genres";
    }
}
