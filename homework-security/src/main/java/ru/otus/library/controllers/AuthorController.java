package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.services.AuthorService;

@Controller
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "authorList";
    }

    @GetMapping("/authors/add")
    public String addAuthorForm(Model model) {
        model.addAttribute("type", "authors");
        return "nameAdd";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@RequestParam("name") String name) {
        authorService.add(name);
        return "redirect:/authors";
    }

    @GetMapping("/authors/edit")
    public String getAuthorEditForm(Model model, @RequestParam("authorId") String authorId) {
        model.addAttribute("editedObject", authorService.getById(authorId));
        model.addAttribute("type", "authors");
        return "nameEdit";
    }

    @PostMapping("/authors/edit")
    public String editAuthor(
            @RequestParam("id") String authorId,
            @RequestParam("name") String name
    ) {
        authorService.update(authorId, name);
        return "redirect:/authors";
    }

    @PostMapping("/authors/delete")
    public String deleteAuthor(@RequestParam("id") String authorId) {
        authorService.delete(authorId);
        return "redirect:/authors";
    }
}
