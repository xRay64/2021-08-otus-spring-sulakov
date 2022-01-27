package ru.otus.homeworkbatch.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@Document
@AllArgsConstructor
public class BookDTO {
    @Id
    private String id;
    private String name;
    private List<AuthorDTO> author;
    private List<GenreDTO> genreList;
}
