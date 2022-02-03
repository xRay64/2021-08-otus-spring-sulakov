package ru.otus.library.actuators;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.library.repositories.BookRepository;

@Component
@RequiredArgsConstructor
public class BooksCountHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        long bookCount = bookRepository.count();
        if (bookCount == 0) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "All books disappeared!")
                    .build();
        } else {
            return Health.up().withDetail("message", "Everything alright").build();
        }
    }
}