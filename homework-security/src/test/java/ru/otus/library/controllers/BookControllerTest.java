package ru.otus.library.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.models.Author;
import ru.otus.library.models.Book;
import ru.otus.library.models.Genre;
import ru.otus.library.repositories.AuthorRepository;
import ru.otus.library.repositories.BookRepository;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.repositories.UserRepository;
import ru.otus.library.security.MyUserDetailService;
import ru.otus.library.services.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

        @Autowired
        private UserRepository userRepository;

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

        @Bean
        public MyUserDetailService myUserDetailService() {
            return new MyUserDetailService(userRepository);
        }
    }

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("возвращать список книг")
    void shouldReturnBooksForm() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("не возвращать форму редактирования книги из-за отсутствия аутентификации")
    void shouldNotReturnAddBookForm() throws Exception {
        mockMvc.perform(get("/book/add"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("возвращать форму редактирования книги")
    void shouldReturnAddBookForm() throws Exception {
        mockMvc.perform(get("/book/add"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("добавлять книгу")
    void shouldAddBook() throws Exception {
        mockMvc.perform(post("/book/add")
                        .param("book-name", "new book")
                        .param("authorId", "author-001, author-002")
                        .param("genreId", "genre-003, genre-004")
                )
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("не возвращать форму редактирования книги из-за отсутствия аутентификации")
    void shouldNotReturnEditBookForm() throws Exception {
        mockMvc.perform(get("/book/edit").param("bookId", "book-001"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("возвращать форму редактирования книги")
    void shouldReturnEditBookForm() throws Exception {
        mockMvc.perform(get("/book/edit").param("bookId", "book-001"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("редактировать книгу")
    void shouldEditBook() throws Exception {
        mockMvc.perform(post("/book/edit")
                        .param("id", "book-004")
                        .param("book-name", "testBookName")
                        .param("authorId", "author-001, author-002")
                        .param("genreId", "genre-003, genre-004"))
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("не возвращать 404 так как нет книги с testBookId из-за отсутствия аутентификации")
    void shouldNotReturnNotFound() throws Exception {
        mockMvc.perform(post("/book/edit")
                        .param("id", "testBookId")
                        .param("book-name", "testBookName")
                        .param("authorId", "author-001, author-002")
                        .param("genreId", "genre-003, genre-004"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin", roles = "ADMIN")
    @Test
    @DisplayName("возвращать 404 так как нет книги с testBookId")
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/book/edit")
                        .param("id", "testBookId")
                        .param("book-name", "testBookName")
                        .param("authorId", "author-001, author-002")
                        .param("genreId", "genre-003, genre-004"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять книгу")
    void shouldDeleteBook() throws Exception {
        mockMvc.perform(post("/book/delete").param("bookId", "book-005"))
                .andExpect(status().isFound());
    }
}