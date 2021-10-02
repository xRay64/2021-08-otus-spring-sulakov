package ru.otus.homeworklibrary.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homeworklibrary.models.BookComment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
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
    public List<BookComment> findByBookId(long id) {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c where c.book.id = :book_id", BookComment.class);
        query.setParameter("book_id", id);
        return query.getResultList();
    }

    @Override
    public List<BookComment> findAll() {
        TypedQuery<BookComment> query = em.createQuery("select c from BookComment c", BookComment.class);
        return query.getResultList();
    }

    @Override
    public void updateById(long id, String text) {
        Query query = em.createQuery("update BookComment set commentText = :comment_text where id = :id");
        query.setParameter("id", id);
        query.setParameter("comment_text", text);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from BookComment where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
