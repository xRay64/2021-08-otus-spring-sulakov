package ru.otus.homework02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.homework02.service.QuizService;

@SpringBootApplication
public class Quiz {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Quiz.class);
        QuizService service = context.getBean(QuizService.class);
        service.startQuiz();
    }

}
