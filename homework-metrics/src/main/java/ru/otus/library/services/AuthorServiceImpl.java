package ru.otus.library.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Author;
import ru.otus.library.repositories.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public void add(String name) {
        authorRepository.save(new Author(ObjectId.get().toString(), name));
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author getById(String id) {
        return authorRepository.findById(id).orElseThrow(() -> new NullPointerException("Wrong Author id: " + id));
    }

    @Override
    public void update(String id, String name) {
        Author updatedAuthor = getById(id);
        updatedAuthor.setName(name);
        authorRepository.save(updatedAuthor);
    }

    @Override
    public void delete(String id) {
        authorRepository.deleteById(id);
    }
}
