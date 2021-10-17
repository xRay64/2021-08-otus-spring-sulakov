package ru.otus.homeworklibrary.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.BookComment;
import ru.otus.homeworklibrary.repositories.BookRepository;
import ru.otus.homeworklibrary.repositories.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepository repository;
    private BookRepository bookRepository;

    @Override
    @Transactional
    public long addComment(String comment_text, long book_id) {
        Book commentedBook = bookRepository.find(book_id).get();
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
    public List<BookComment> getByBook(Book book) {
        return repository.findByBook(book);
    }

    @Override
    @Transactional
    public void updateComment(long id, String comment_text) {
        repository.updateById(id, comment_text);
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        repository.delete(id);
    }
}
