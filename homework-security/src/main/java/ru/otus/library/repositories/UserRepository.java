package ru.otus.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.library.models.User;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByUsername(String username);
}
