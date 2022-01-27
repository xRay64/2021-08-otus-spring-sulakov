package ru.otus.homeworkbatch.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Document
@AllArgsConstructor
public class GenreDTO {
    @Id
    private String id;
    private String name;
}
