package ru.otus.library.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private List<String> roles;
    private boolean enabled;
}
