package ru.otus.library.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.otus.library.services.ext.Sleepper.sleepRandomly;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    @HystrixCommand(fallbackMethod = "fallbackGetBookComments", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public List<Comment> getBookComments(String bookId) {
        sleepRandomly();
        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);
        return book.getCommentList();
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @Override
    public void addBookComment(String bookId, String commentText) {
        sleepRandomly();
        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);
        List<Comment> commentList = book.getCommentList();
        if (commentList == null) { // Если у книги еще нет комментариев
            commentList = new ArrayList<>();
        }
        commentList.add(new Comment(ObjectId.get().toString(), commentText));
        book.setCommentList(commentList);
        bookRepository.save(book);
    }

    private List<Comment> fallbackGetBookComments(String bookId) {
        return List.of(new Comment("N/A", "N/A"));
    }
}
