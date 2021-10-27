package ru.otus.library.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Author> findAllAuthors() {
        Aggregation aggregation = newAggregation(
                unwind("authorList")
                , project().andExclude("_id").and("authorList.id").as("_id").and("authorList.name").as("name")
                , group("_id", "name")
                , project().and("_id._id").as("_id").and("_id.name").as("name")
                , sort(Sort.Direction.ASC, "_id")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Author.class).getMappedResults();
    }

    @Override
    public List<Genre> findAllGenres() {
        Aggregation aggregation = newAggregation(
                unwind("genreList")
                , project().and("genreList.id").as("_id").and("genreList.name").as("name")
                , group("_id", "name")
                , project().and("_id._id").as("_id").and("_id.name").as("name")
                , sort(Sort.Direction.ASC, "_id")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Genre.class).getMappedResults();
    }
}
