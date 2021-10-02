package ru.otus.homeworklibrary.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homeworklibrary.models.Genre;
import ru.otus.homeworklibrary.repositories.GenreRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class GenresServiceImpl implements GenresService{

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional
    public Genre getGenre(long id) {
        return genreRepository.findById(id).orElseThrow(() -> new RuntimeException("Genre id isn't exists"));
    }

    @Override
    @Transactional
    public void updateGenre(long id, String name) {
        genreRepository.updateNameById(id, name);
    }

    @Override
    @Transactional
    public long addGenre(String name) {
        Genre newGenre = genreRepository.save(new Genre(0, name));
        return newGenre.getId();
    }

    @Override
    @Transactional
    public void deleteGenre(long id) {
        genreRepository.deleteById(id);
    }
}
