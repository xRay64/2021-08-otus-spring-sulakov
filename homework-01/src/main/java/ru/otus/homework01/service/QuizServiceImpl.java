package ru.otus.homework01.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.homework01.dao.QuizData;
import ru.otus.homework01.domain.Question;
import ru.otus.homework01.domain.User;

@PropertySource("classpath:application.properties")
@Service
public class QuizServiceImpl implements QuizService {
    private final QuizData quizData;
    private final UserInteractionService userInteractionService;
    private final int scoreToWin;
    private User user;

    public QuizServiceImpl(QuizData quizDataCSV, UserInteractionService userInteractionService, @Value("${quiz.score-to-vin}") int scoreToWin) {
        this.quizData = quizDataCSV;
        this.userInteractionService = userInteractionService;
        this.scoreToWin = scoreToWin;
    }

    @Override
    public void startQuiz() {
        //Если начинаем квиз - то подготовим данные
        quizData.prepareData();
        //попросим пользователя представиться
        String userNameString = userInteractionService.askUserForString("Please, introduce yourself:");
        if (userNameString != null) {
            this.user = new User(userNameString);
        }
        //начинаем игру
        userInteractionService.promptUser(String.format("Hello %s, let me ask you some questions.\n" +
                "For win this game you need to answer %d questions correctly.", user.getUserName(), 3));
        //В цикле зададим вопросы и соберем ответы
        while (quizData.hasNext()) {
            Question currentQuestion = quizData.getNextQuestion();
            System.out.print(currentQuestion);
            if (currentQuestion.needTextAnswer()) {
                String userAnswerString = userInteractionService.askUserForString("Enter a text answer. Please.");
                if (currentQuestion.checkResponseString(userAnswerString)) {
                    user.upScore();
                }
            } else {
                int userAnswer = userInteractionService.askUserForInt("Enter the number of answer. Please.");
                if (currentQuestion.checkResponseIndex(userAnswer)) {
                    user.upScore();
                }
            }
        }
        //возьмем паузу на подсчет очков
        userInteractionService.promptUser("Let me think...");
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            userInteractionService.promptUser("...");
        }
        if (user.getScore() >= scoreToWin) {
            System.out.println("Congrats!!! You are win!");
        } else {
            System.out.println("Sorry :( You are looser.");
        }
    }

    public void printAllQuestions() {
        while (quizData.hasNext()) {
            System.out.print(quizData.getNextQuestion());
            System.out.println("----------------------------------------");
        }
    }
}
