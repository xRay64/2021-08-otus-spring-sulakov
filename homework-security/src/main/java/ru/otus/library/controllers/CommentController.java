package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.library.services.CommentService;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/book/comments")
    public String getCommentsForm(@RequestParam("bookId") String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        model.addAttribute("comments", commentService.getBookComments(bookId));
        return "bookComments";
    }

    @PostMapping("/book/comments")
    public String addComment(
            @RequestParam("bookId") String bookId,
            @RequestParam("commentText") String commentText
    ) {
        commentService.addBookComment(bookId, commentText);
        return "redirect:/";
    }
}
