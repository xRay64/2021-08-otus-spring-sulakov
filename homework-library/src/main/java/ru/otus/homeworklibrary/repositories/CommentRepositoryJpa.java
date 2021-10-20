package ru.otus.homeworklibrary.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.homeworklibrary.models.Book;
import ru.otus.homeworklibrary.models.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(c) from BookComment c", Long.class);
        return query.getSingleResult();
    }

    @Override
    public BookComment save(BookComment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public Optional<BookComment> findById(long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public List<BookComment> findByBook(Book book) {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c where c.book = :book", BookComment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public List<BookComment> findByBooks(List<Book> books) {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c where c.book in :books", BookComment.class);
        query.setParameter("books", books);
        return query.getResultList();
    }

    @Override
    public List<BookComment> findAll() {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c", BookComment.class);
        return query.getResultList();
    }

    @Override
    public void updateById(long id, String text) {
        BookComment comment = em.find(BookComment.class, id);
        comment.setCommentText(text);
        em.merge(comment);
    }

    @Override
    public void update(BookComment comment) {
        em.merge(comment);
    }

    @Override
    public void delete(long id) {
        BookComment comment = em.find(BookComment.class, id);
        em.remove(comment);
    }
}
