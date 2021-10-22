package ru.otus.homeworklibrary.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.BookComment;
import ru.otus.homeworklibrary.repositories.BookRepository;
import ru.otus.homeworklibrary.repositories.CommentRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepository repository;
    private BookService bookService;

    @Override
    @Transactional
    public long addComment(String comment_text, long book_id) {
        Book commentedBook = bookService.get(book_id);
        BookComment newComment = repository.save(new BookComment(0, comment_text, commentedBook));
        return newComment.getId();
    }

    @Override
    public List<BookComment> getAllComments() {
        return repository.findAll();
    }

    @Override
    public BookComment getComment(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("error while getting Comment with id " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookComment> getByBookId(long bookId) {
        Book book = bookService.get(bookId);
        book.getBookComments().size();
        return book.getBookComments();
    }

    @Override
    @Transactional
    public void updateComment(long id, String comment_text) {
        BookComment bookComment = repository.findById(id).orElseThrow(() -> new RuntimeException("error while getting comment in updateComment"));
        bookComment.setCommentText(comment_text);
    }

    @Override
    public void deleteCommentById(long id) {
        repository.deleteById(id);
    }
}
