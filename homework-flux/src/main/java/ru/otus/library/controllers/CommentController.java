package ru.otus.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.BookRepository;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final BookRepository bookRepository;

    @GetMapping("/book/{id}/comments")
    public Flux<Comment> getCommentsForm(@PathVariable("id") String bookId) {
         return bookRepository
                .findById(bookId)
                .flatMapMany(book -> Flux.fromIterable(book.getCommentList()));
    }

    @PostMapping("/book/{id}/comment")
    public void addComment(
            @PathVariable("id") String bookId,
            @RequestParam("commentText") String commentText
    ) {
        bookRepository.findById(bookId)
                .map(book -> {
                    book.getCommentList().add(new Comment(ObjectId.get().toString(), commentText));
                    return book;
                })
                .subscribe(book -> bookRepository.save(book).subscribe());
    }
}
