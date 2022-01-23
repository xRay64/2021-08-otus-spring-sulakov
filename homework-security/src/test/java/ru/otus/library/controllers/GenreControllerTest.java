package ru.otus.library.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.repositories.GenreRepository;
import ru.otus.library.repositories.UserRepository;
import ru.otus.library.security.MyUserDetailService;
import ru.otus.library.services.GenreService;
import ru.otus.library.services.GenreServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataMongo
@WebMvcTest(GenreController.class)
@DisplayName("GenreController дожен")
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class AddConfig {
        @Autowired
        private GenreRepository genreRepository;

        @Autowired
        private UserRepository userRepository;

        @Bean
        public GenreService genreService() {
            return new GenreServiceImpl(genreRepository);
        }

        @Bean
        public MyUserDetailService myUserDetailService() {
            return new MyUserDetailService(userRepository);
        }
    }

    @Autowired
    private GenreService genreService;

    @Test
    @DisplayName("возвращать форму со списком жанров")
    void shouldGetGenresForm() throws Exception {
        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("редирект на форму логина при запросе формы добавления без аутентификации")
    void shouldNotReturnAddGenreForm() throws Exception {
        mockMvc.perform(get("/genres/add"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin")
    @Test
    @DisplayName("возвращать форму добавления жанра")
    void shouldReturnAddGenreForm() throws Exception {
        mockMvc.perform(get("/genres/add"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("добавлять жанр")
    void shouldAddGenre() throws Exception {
        mockMvc.perform(post("/genres/add")
                        .param("name", "new genre"))
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("редирект на форму логина при запросе формы редактирования без аутентификации")
    void shouldNotReturnGenreEditForm() throws Exception {
        mockMvc.perform(get("/genres/edit").param("genreId", "genre-002"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin")
    @Test
    @DisplayName("возвращать форму редактирования жанра")
    void shouldReturnGenreEditForm() throws Exception {
        mockMvc.perform(get("/genres/edit").param("genreId", "genre-002"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("редактировать жанр")
    void shouldEditGenre() throws Exception {
        mockMvc.perform(post("/genres/edit")
                        .param("id", "genre-001")
                        .param("name", "testGenreName"))
                .andExpect(status().isFound());
    }

    @Test
    @DisplayName("редирект на форму логина при запросе несуществующего жанра без аутентификации")
    void shouldNotReturnNotFound() throws Exception {
        mockMvc.perform(post("/genres/edit")
                        .param("id", "testGenreId")
                        .param("name", "testGenreName"))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser(username = "admin")
    @Test
    @DisplayName("редактировать жанр")
    void shouldReturnNotFound() throws Exception {
        mockMvc.perform(post("/genres/edit")
                        .param("id", "testGenreId")
                        .param("name", "testGenreName"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("удалять жанр")
    void shouldDeleteGenre() throws Exception {
        mockMvc.perform(post("/genres/delete").param("id","genre-004"))
                .andExpect(status().isFound());
    }
}