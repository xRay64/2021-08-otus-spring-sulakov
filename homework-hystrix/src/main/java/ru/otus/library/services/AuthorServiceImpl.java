package ru.otus.library.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Author;
import ru.otus.library.repositories.AuthorRepository;

import java.util.List;

import static ru.otus.library.services.ext.Sleepper.sleepRandomly;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @HystrixCommand(commandProperties= {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    @Override
    public void add(String name) {
        authorRepository.save(new Author(ObjectId.get().toString(), name));
    }

    @HystrixCommand(fallbackMethod="fallbackGetAll", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public List<Author> getAll() {
        sleepRandomly();
        return authorRepository.findAll();
    }

    @HystrixCommand(fallbackMethod="fallbackGetById", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public Author getById(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new NullPointerException("Wrong Author id: " + id));
    }

    @HystrixCommand(commandProperties= {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    @Override
    public void update(String id, String name) {
        Author updatedAuthor = getById(id);
        updatedAuthor.setName(name);
        authorRepository.save(updatedAuthor);
    }

    @HystrixCommand(commandProperties= {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    @Override
    public void delete(String id) {
        authorRepository.deleteById(id);
    }

    private List<Author> fallbackGetAll() {
        return List.of(new Author("N/A", "N/A"));
    }

    private Author fallbackGetById() {
        return new Author("N/A", "N/A");
    }
}
