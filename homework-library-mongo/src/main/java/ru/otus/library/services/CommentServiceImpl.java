package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;
import ru.otus.library.models.Comment;
import ru.otus.library.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    public static final String COMMENT_ID_PREFIX = "comment-";

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> getCommentsByBook(Book book) {
        return commentRepository.getCommentByBook(book);
    }

    @Override
    public void add(Book book, String commentText) {
        String newCommentId = COMMENT_ID_PREFIX + String.format("%03d",commentRepository.count() + 1);
        Comment newComment = new Comment(newCommentId, commentText, book);
        commentRepository.save(newComment);
    }

    @Override
    public void update(String commentId, String newCommentText) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment editedComment = null;
        if (optionalComment.isEmpty()) {
            System.out.println("Не нашлось комментария с Id " + commentId);
        } else {
            editedComment = optionalComment.get();
        }
        editedComment.setText(newCommentText);
        commentRepository.save(editedComment);
    }

    @Override
    public void delete(String commentId) {
        commentRepository.deleteById(commentId);
    }
}
