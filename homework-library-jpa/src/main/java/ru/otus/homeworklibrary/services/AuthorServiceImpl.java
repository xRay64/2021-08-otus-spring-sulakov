package ru.otus.homeworklibrary.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homeworklibrary.models.Author;
import ru.otus.homeworklibrary.repositories.AuthorRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthor(long id) {
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author id isn't exists"));
    }

    @Override
    @Transactional
    public void updateAuthor(long id, String name) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("error while getting author in updateAuthor"));
        author.setName(name);
    }

    @Override
    public long addAuthor(String name) {
        Author newAuthor = authorRepository.save(new Author(0, name));
        return newAuthor.getId();
    }

    @Override
    public void deleteAuthor(long id) {
        authorRepository.deleteById(id);
    }
}
