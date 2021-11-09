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
import ru.otus.library.services.AuthorService;
import ru.otus.library.services.AuthorServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AuthorController должен")
@WebMvcTest(AuthorController.class)
@AutoConfigureDataMongo
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class AddConfig {
        @Autowired
        private AuthorRepository authorRepository;

        @Bean
        public AuthorService authorService() {
            return new AuthorServiceImpl(authorRepository);
        }
    }

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("отображать всех авторов")
    void shouldGetAuthors() throws Exception {
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("возвращать форму добавления автора")
    void shouldReturnAddAuthorForm() throws Exception {
        mockMvc.perform(get("/authors/add"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("добавлять автора")
    void shouldAddAuthor() throws Exception {
        mockMvc.perform(post("/authors/add")
                                .param("name", "new author"))
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("возвращать форму редактирования автора")
    void shouldReturnAuthorEditForm() throws Exception {
        mockMvc.perform(get("/authors/edit").param("authorId", "author-002"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("редактировать автора")
    void shouldEditAuthor() throws Exception {
        mockMvc.perform(post("/authors/edit")
                        .param("id", "author-001")
                        .param("name", "testAuthorName"))
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("возвращать 404 так как нет автора с testAuthorId")
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/authors/edit")
                        .param("id", "testAuthorId")
                        .param("name", "testAuthorName"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять автора")
    void shouldDeleteAuthor() throws Exception {
        mockMvc.perform(post("/authors/delete").param("id","testAuthorId"))
                .andExpect(status().isFound());
    }
}