package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    @Override
    public List<Comment> getBookComments(String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);
        return book.getCommentList();
    }

    @Override
    public void addBookComment(String bookId, String commentText) {
        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);
        List<Comment> commentList = book.getCommentList();
        if (commentList == null) { // Если у книги еще нет комментариев
            commentList = new ArrayList<>();
        }
        commentList.add(new Comment(ObjectId.get().toString(), commentText));
        book.setCommentList(commentList);
        bookRepository.save(book);
    }
}
