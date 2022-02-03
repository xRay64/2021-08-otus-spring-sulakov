package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.models.Comment;
import ru.otus.library.services.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/book/{id}/comments")
    public List<Comment> getCommentsForm(@PathVariable("id") String bookId) {
        return commentService.getBookComments(bookId);
    }

    @PostMapping("/book/{id}/comment")
    public void addComment(
            @PathVariable("id") String bookId,
            @RequestParam("commentText") String commentText
    ) {
        commentService.addBookComment(bookId, commentText);
    }
}
