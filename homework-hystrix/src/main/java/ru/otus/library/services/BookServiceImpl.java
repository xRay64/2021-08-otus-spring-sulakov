package ru.otus.library.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.models.Book;
import ru.otus.library.repositories.BookRepository;

import java.util.List;

import static ru.otus.library.services.ext.Sleepper.sleepRandomly;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @HystrixCommand(fallbackMethod="fallbackGetAll", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public List<Book> getAll() {
        sleepRandomly();
        return bookRepository.findAll();
    }

    @HystrixCommand(fallbackMethod="fallbackGetByID", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @Override
    public Book getById(String bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new NullPointerException("Book with id \"" + bookId + "\" doesn't exist"));
    }

    @HystrixCommand(commandProperties= {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @HystrixCommand(commandProperties= {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    @Override
    public void deleteById(String bookId) {
        bookRepository.deleteById(bookId);
    }

    @HystrixCommand(commandProperties= {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    @Override
    public long count() {
        return bookRepository.count();
    }

    private List<Book> fallbackGetAll() {
        return List.of(new Book("N/A", "N/A", List.of(), List.of(), List.of()));
    }

    private Book fallbackGetById() {
        return new Book("N/A", "N/A", List.of(), List.of(), List.of());
    }
}
