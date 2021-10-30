package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{

    private final GenreRepository genreRepository;


    @Override
    public void add(String name) {
        genreRepository.save(new Genre(ObjectId.get().toString(), name));
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public void update(String id, String name) {
        Genre updatedGenre = getById(id);
        updatedGenre.setName(name);
        genreRepository.save(updatedGenre);
    }

    @Override
    public void delete(String id) {
        genreRepository.deleteById(id);
    }
}
