package ru.otus.homeworklibrary.repositories;

import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DisplayName("Репозиторий комментариев")
@DataJpaTest
class CommentRepositoryTest {

    private static final int EXPECTED_QUERY_COUNT = 1;

    @Autowired
    private CommentRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("должен выбирать все комментарии определенным количеством запросов в БД")
    void findAll() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        repository.findAll();

        long numberOfQueries = sessionFactory.getStatistics().getPrepareStatementCount();
        System.out.println("---------------------------------------------");
        System.out.println("Number of queries: " + numberOfQueries);
        System.out.println("---------------------------------------------");

        Assertions.assertThat(numberOfQueries).isEqualTo(EXPECTED_QUERY_COUNT);
    }
}