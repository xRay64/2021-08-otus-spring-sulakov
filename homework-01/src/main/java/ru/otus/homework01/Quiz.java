package ru.otus.homework01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.homework01.service.QuizService;

@Configuration
@ComponentScan
public class Quiz {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Quiz.class);
        QuizService service = context.getBean(QuizService.class);
        service.startQuiz();
    }
}
