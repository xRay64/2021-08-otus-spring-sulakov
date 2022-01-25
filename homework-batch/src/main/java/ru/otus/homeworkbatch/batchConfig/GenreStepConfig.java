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
import ru.otus.homeworkbatch.models.Genre;
import ru.otus.homeworkbatch.models.dto.AuthorDTO;
import ru.otus.homeworkbatch.models.dto.GenreDTO;
import ru.otus.homeworkbatch.models.matcher.GenreIdMatcher;
import ru.otus.homeworkbatch.repositories.GenreRepository;

import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class GenreStepConfig {
    private static final int CHUNK_SIZE = 2;
    private final Logger logger = LoggerFactory.getLogger(GenreStepConfig.class);
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step genreTransferStep(ItemReader<Genre> genreReader, MongoItemWriter<GenreDTO> genreWriter,
                                  ItemProcessor<Genre, GenreDTO> genreProcessor) {
        return stepBuilderFactory.get("genreTransferStep")
                .<Genre, GenreDTO>chunk(CHUNK_SIZE)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения жанра");
                    }

                    public void afterRead(@NonNull Genre o) {
                        logger.info("Конец чтения жанра");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения жанра");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи жанра");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи жанра");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи жанра");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Genre o) {
                        logger.info("Начало обработки жанра");
                    }

                    public void afterProcess(@NonNull Genre o, GenreDTO o2) {
                        logger.info("Конец обработки жанра");
                    }

                    public void onProcessError(@NonNull Genre o, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки жанров");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки жанров");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки жанров");
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemReader<Genre> genreReader(GenreRepository genreRepository) {
        return new RepositoryItemReaderBuilder<Genre>()
                .name("GenreRepositoryReader")
                .repository(genreRepository)
                .methodName("findAll")
                .sorts(Map.of("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Genre, GenreDTO> genreProcessor(GenreIdMatcher genreIdMatcher) {
        return genre -> {
            String genreStringId = genreIdMatcher.getGenreStringId(genre.getId());
            return new GenreDTO(genreStringId, genre.getName());
        };
    }

    @Bean
    public MongoItemWriter<GenreDTO> genreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<GenreDTO>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }
}
