package ru.otus.homeworklibrary.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homeworklibrary.models.Genre;
import ru.otus.homeworklibrary.repositories.GenreRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class GenresServiceImpl implements GenresService{

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenre(long id) {
        return genreRepository.findById(id).orElseThrow(() -> new RuntimeException("Genre id isn't exists"));
    }

    @Override
    @Transactional
    public void updateGenre(long id, String name) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new RuntimeException("error while getting genre in updateGenre"));
        genre.setName(name);
    }

    @Override
    public long addGenre(String name) {
        Genre newGenre = genreRepository.save(new Genre(0, name));
        return newGenre.getId();
    }

    @Override
    public void deleteGenre(long id) {
        genreRepository.deleteById(id);
    }
}
