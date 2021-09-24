package ru.otus.homeworklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.h2.tools.Console;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.homeworklibrary.dao.AuthorDAO;
import ru.otus.homeworklibrary.domain.Author;

import java.sql.SQLException;

@SpringBootApplication
public class HomeworkLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkLibraryApplication.class, args);
    }

}
