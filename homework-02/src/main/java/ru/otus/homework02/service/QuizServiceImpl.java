package ru.otus.homework02.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework02.config.QuizConfig;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.User;

import java.util.Locale;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;
    private final UserInteractionService userInteractionService;
    private final int scoreToWin;
    private User user;
    private int countOfAskedQuestions;
    private MessageSource messageSource;

    public QuizServiceImpl(QuestionService questionService, UserInteractionService userInteractionService, QuizConfig quizConfig, MessageSource messageSource) {
        this.questionService = questionService;
        this.userInteractionService = userInteractionService;
        this.scoreToWin = quizConfig.getScoreToWin();
        this.messageSource = messageSource;
    }

    @Override
    public void startQuiz() {
        //попросим пользователя представиться
        String userNameString = userInteractionService.askUserForString(
                messageSource.getMessage("strings.greetings", null, Locale.getDefault()));
        if (userNameString != null) {
            this.user = new User(userNameString);
        }
        //начинаем игру
        /*userInteractionService.promptUser(String.format("Hello %s, let me ask you some questions.\n" +
                "For win this game you need to answer %d questions correctly.", user.getUserName(), 3));*/
        userInteractionService.promptUser(messageSource.getMessage(
                "strings.hello", new String[]{user.getUserName(), String.valueOf(scoreToWin)}, Locale.getDefault()));
        //В цикле зададим вопросы и соберем ответы
        while (questionService.hasNext()) {
            Question currentQuestion = questionService.getNextQuestion();
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
            countOfAskedQuestions++;
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

        int resultPercent = 100 / countOfAskedQuestions * user.getScore();

        if (user.getScore() >= scoreToWin) {
            System.out.printf("Congrats!!! You are win!\nWith result: %d%n", resultPercent);
        } else {
            System.out.printf("Sorry :( You are looser.\nWith result: %d%n", resultPercent);
        }
    }

    public void printAllQuestions() {
        while (questionService.hasNext()) {
            System.out.print(questionService.getNextQuestion());
            System.out.println("----------------------------------------");
        }
    }
}