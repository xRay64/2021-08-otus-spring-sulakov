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
        ConfigurableApplicationContext context = SpringApplication.run(HomeworkLibraryApplication.class, args);

        try {
            Console.main(args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AuthorDAO bean = context.getBean(AuthorDAO.class);
        System.out.println(bean.count()); //todo все тут почистить

//        System.out.println(bean.getById(5));
        for (Author author : bean.getAll()) {
            System.out.println(author);
        }

    }

}
