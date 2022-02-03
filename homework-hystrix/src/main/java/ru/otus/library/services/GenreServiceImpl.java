package ru.otus.library.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Author;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.GenreRepository;

import java.util.List;

import static ru.otus.library.services.ext.Sleepper.sleepRandomly;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @Override
    public void add(String name) {
        genreRepository.save(new Genre(ObjectId.get().toString(), name));
    }

    @HystrixCommand(fallbackMethod = "fallbackGetAll", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "fallbackGetById", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public Genre getById(String id) {
        sleepRandomly();
        return genreRepository.findById(id).orElseThrow(() -> new NullPointerException("Wrong Genre id: " + id));
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @Override
    public void update(String id, String name) {
        Genre updatedGenre = getById(id);
        updatedGenre.setName(name);
        genreRepository.save(updatedGenre);
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @Override
    public void update(Genre genre) {
        genreRepository.save(genre);
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @Override
    public void delete(String id) {
        genreRepository.deleteById(id);
    }

    private List<Genre> fallbackGetAll() {
        return List.of(new Genre("N/A", "N/A"));
    }

    private Genre fallbackGetById() {
        return new Genre("N/A", "N/A");
    }
}
