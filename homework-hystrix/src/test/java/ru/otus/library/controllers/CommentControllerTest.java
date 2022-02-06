package ru.otus.library.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.library.models.Comment;
import ru.otus.library.services.CommentService;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CommentController должен")
@WebMvcTest(CommentController.class)
@AutoConfigureDataMongo
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("при запросе комментов возвращать статус Ок")
    public void shouldReturnStatusOK() throws Exception {
        when(commentService.getBookComments(any()))
                .thenReturn(List.of(new Comment("abcd123", "test comment")));
        mockMvc.perform(get("/book/book-01/comments").param("bookId", "book-001"))
                .andExpect(status().isOk());
    }
}