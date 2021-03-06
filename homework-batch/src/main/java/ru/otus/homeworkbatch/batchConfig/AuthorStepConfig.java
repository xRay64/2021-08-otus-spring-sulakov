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
import ru.otus.homeworkbatch.models.dto.AuthorDTO;
import ru.otus.homeworkbatch.models.matcher.AuthorIdMatcher;
import ru.otus.homeworkbatch.repositories.AuthorRepository;

import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AuthorStepConfig {
    private static final int CHUNK_SIZE = 2;
    private final Logger logger = LoggerFactory.getLogger(AuthorStepConfig.class);
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step authorTransferStep(ItemReader<Author> authorReader, MongoItemWriter<AuthorDTO> authorWriter,
                                     ItemProcessor<Author, AuthorDTO> authorProcessor) {
        return stepBuilderFactory.get("authorTransferStep")
                .<Author, AuthorDTO>chunk(CHUNK_SIZE)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("???????????? ???????????? ????????????");
                    }

                    public void afterRead(@NonNull Author o) {
                        logger.info("?????????? ???????????? ????????????");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("???????????? ???????????? ????????????");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("???????????? ???????????? ????????????");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("?????????? ???????????? ????????????");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("???????????? ???????????? ????????????");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Author o) {
                        logger.info("???????????? ?????????????????? ????????????");
                    }

                    public void afterProcess(@NonNull Author o, AuthorDTO o2) {
                        logger.info("?????????? ?????????????????? ????????????");
                    }

                    public void onProcessError(@NonNull Author o, @NonNull Exception e) {
                        logger.info("???????????? ??????????????????");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("???????????? ?????????? ??????????????");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("?????????? ?????????? ??????????????");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("???????????? ?????????? ??????????????");
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemReader<Author> authorReader(AuthorRepository authorRepository) {
        return new RepositoryItemReaderBuilder<Author>()
                .name("AuthorRepositoryReader")
                .repository(authorRepository)
                .methodName("findAll")
                .sorts(Map.of("id", Sort.Direction.DESC))
                .build();
    }

    @Bean
    public ItemProcessor<Author, AuthorDTO> authorProcessor(AuthorIdMatcher authorIdMatcher) {
        return author -> {
            String authorStringId = authorIdMatcher.getAuthorStringId(author.getId());
            return new AuthorDTO(authorStringId, author.getName());
        };
    }

    @Bean
    public MongoItemWriter<AuthorDTO> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<AuthorDTO>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }
}
