package ru.otus.homeworkbatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongoCleanUpService {
    private final MongoTemplate mongoTemplate;

    public void cleanUpMongo() {
        mongoTemplate.dropCollection("authors");
        mongoTemplate.dropCollection("genres");
        mongoTemplate.dropCollection("books");
    }
}
