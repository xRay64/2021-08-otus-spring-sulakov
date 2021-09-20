package ru.otus.homeworklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.h2.tools.Console;

import java.sql.SQLException;

@SpringBootApplication
public class HomeworkLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkLibraryApplication.class, args);

        try {
            Console.main(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
