package ru.otus.homeworklibrary.repositories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.homeworklibrary.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class GenreRepositoryJpa implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(g) from Genre g", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public List<Genre> findByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void updateNameById(long id, String name) {
        Query query = em.createQuery("update Genre set name = :name where id = :id");
        query.setParameter("id", id);
        query.setParameter("name", name);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Genre where id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long getMaxId() {
        TypedQuery<Long> query = em.createQuery("select max(id) from Genre", Long.class);
        return query.getSingleResult();
    }
}
