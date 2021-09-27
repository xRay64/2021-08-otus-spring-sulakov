package ru.otus.homeworklibrary.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.homeworklibrary.dao.AuthorDAO;
import ru.otus.homeworklibrary.domain.Author;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDAO authorDAO;

    @Override
    public List<Author> getAllAuthors() {
        return authorDAO.getAll();
    }

    @Override
    public Author getAuthor(long id) {
        return authorDAO.getById(id);
    }

    @Override
    public void updateAuthor(long id, String name) {
        authorDAO.update(new Author(id, name));
    }

    @Override
    public long addAuthor(String name) {
        return authorDAO.insert(new Author(-1, name));
    }

    @Override
    public void deleteAuthor(long id) {
        authorDAO.deleteById(id);
    }
}
