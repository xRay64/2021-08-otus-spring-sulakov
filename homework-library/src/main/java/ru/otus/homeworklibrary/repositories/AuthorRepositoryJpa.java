package ru.otus.homeworklibrary.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homeworklibrary.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(a) from Author a", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public Optional<Author> find(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public List<Author> findByName(String name) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name", Author.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void updateNameById(long id, String name) {
        Query query = em.createQuery("update Author a set a.name = :name where a.id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public void delteById(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long getMaxId() {
        TypedQuery<Long> query = em.createQuery("select max(id) from Author", Long.class);
        return query.getSingleResult();
    }

}
