package ru.otus.library.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.services.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataMongo
@WebMvcTest(BookController.class)
@DisplayName("BookController дожен")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class AddConfig {
        @Autowired
        private AuthorRepository authorRepository;

        @Autowired
        private GenreRepository genreRepository;

        @Autowired
        private BookRepository bookRepository;

        @Bean
        public AuthorService authorService() {
            return new AuthorServiceImpl(authorRepository);
        }

        @Bean
        public GenreService genreService() {
            return new GenreServiceImpl(genreRepository);
        }

        @Bean
        public BookService bookService() {
            return new BookServiceImpl(bookRepository);
        }
    }

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("возвращать список книг")
    void shouldReturnBooksForm() throws Exception {
        mockMvc.perform(get("/books"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("добавлять книгу")
    void shouldAddBook() throws Exception {
        mockMvc.perform(post("/book")
                        .contentType("application/json")
                        .content("{\"name\":\"TestBook\",\"authors\":[{\"id\":\"author-001\",\"name\":\"Test author 1\"}],\"genres\":[{\"id\":\"genre-001\",\"name\":\"Test genre 1\"}]}")
                )
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("редактировать книгу")
    void shouldEditBook() throws Exception {
        mockMvc.perform(put("/book/book-004")
                        .contentType("application/json")
                        .content("{\"name\":\"testBookName\",\"authors\":[{\"id\":\"author-001\",\"name\":\"Test author 1\"}],\"genres\":[{\"id\":\"genre-001\",\"name\":\"Test genre 1\"}]}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("возвращать 404 так как нет книги с testBookId")
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/book/book-006")
                        .contentType("application/json")
                        .content("{\"name\":\"testBookName\",\"authors\":[{\"id\":\"author-001\",\"name\":\"Test author 1\"}],\"genres\":[{\"id\":\"genre-001\",\"name\":\"Test genre 1\"}]}"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять книгу")
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(delete("/book/book-001"))
                .andExpect(status().isOk());
    }
}