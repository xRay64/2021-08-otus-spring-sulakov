package ru.otus.homeworklibrary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homeworklibrary.dao.GenreDAO;
import ru.otus.homeworklibrary.domain.Genre;

import java.util.List;

@Service
@AllArgsConstructor
public class GenresServiceImpl implements GenresService{

    private final GenreDAO genreDAO;

    @Override
    public List<Genre> getAllGenres() {
        return genreDAO.getAll();
    }

    @Override
    public Genre getGenre(long id) {
        return genreDAO.getById(id);
    }

    @Override
    public void updateGenre(long id, String name) {
        genreDAO.update(new Genre(id, name));
    }

    @Override
    public long addGenre(String name) {
        return genreDAO.insert(new Genre(-1, name));
    }

    @Override
    public void deleteGenre(long id) {
        genreDAO.deleteById(id);
    }
}
