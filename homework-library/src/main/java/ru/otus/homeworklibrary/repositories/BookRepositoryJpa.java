package ru.otus.homeworklibrary.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homeworklibrary.models.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(b) from Book b", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public Optional<Book> find(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public List<Book> findByName(String bookNmae) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.name = :name", Book.class);
        query.setParameter("name", bookNmae);
        return query.getResultList();
    }

    @Override
    public void update(Book book) {
        if (find(book.getId()).isPresent()) {
            em.merge(book);
        }
    }

    @Override
    public void updateNameById(long id, String name) {
        Query query = em.createQuery("update Book set name = :name where id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
