package ru.otus.homeworkbatch.batchConfig;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import ru.otus.homeworkbatch.models.Author;
import ru.otus.homeworkbatch.models.Book;
import ru.otus.homeworkbatch.models.Genre;
import ru.otus.homeworkbatch.models.dto.AuthorDTO;
import ru.otus.homeworkbatch.models.dto.BookDTO;
import ru.otus.homeworkbatch.models.dto.GenreDTO;
import ru.otus.homeworkbatch.models.matcher.AuthorIdMatcher;
import ru.otus.homeworkbatch.models.matcher.GenreIdMatcher;
import ru.otus.homeworkbatch.repositories.AuthorRepository;
import ru.otus.homeworkbatch.repositories.BookRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class BooksStepConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger(BooksStepConfig.class);
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step bookTransferStep(ItemReader<Book> bookReader, MongoItemWriter<BookDTO> bookWriter,
                                   ItemProcessor<Book, BookDTO> bookProcessor) {
        return stepBuilderFactory.get("bookTransferStep")
                .<Book, BookDTO>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения книги");
                    }

                    public void afterRead(@NonNull Book o) {
                        logger.info("Конец чтения книги");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения книги");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи книги");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи книги");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи автора");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Book o) {
                        logger.info("Начало обработки книги");
                    }

                    public void afterProcess(@NonNull Book o, BookDTO o2) {
                        logger.info("Конец обработки книги");
                    }

                    public void onProcessError(@NonNull Book o, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки книг");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки книг");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки авторов");
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemReader<Book> bookReader(BookRepository bookRepository) {
        return new RepositoryItemReaderBuilder<Book>()
                .name("BookRepositoryReader")
                .repository(bookRepository)
                .methodName("findAll")
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Book, BookDTO> bookProcessor(AuthorIdMatcher authorIdMatcher, GenreIdMatcher genreIdMatcher) {
        return book -> {
            List<AuthorDTO> authorDTOList = book.getAuthor().stream()
                    .map(author -> new AuthorDTO(authorIdMatcher.getAuthorStringId(author.getId()), author.getName())).toList();
            List<GenreDTO> genreDTOList = book.getGenreList().stream().map(genre -> new GenreDTO(genreIdMatcher.getGenreStringId(genre.getId()), genre.getName())).toList();
            return new BookDTO(UUID.randomUUID().toString(), book.getName(), authorDTOList, genreDTOList);
        };
    }

    @Bean
    public MongoItemWriter<BookDTO> bookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<BookDTO>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }
}
