package ru.otus.homework02.service;

import org.springframework.stereotype.Service;
import ru.otus.homework02.config.QuizConfig;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.domain.User;
import ru.otus.homework02.helper.IOService;
import ru.otus.homework02.helper.QuestionPrinter;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuestionService questionService;
    private final UserInteractionService userInteractionService;
    private final QuizConfig quizConfig;
    private final QuizMessageSource quizMessageSource;
    private final QuestionPrinter questionPrinter;
    private final IOService ioService;
    private User user;
    private int countOfAskedQuestions;

    public QuizServiceImpl(QuestionService questionService,
                           UserInteractionService userInteractionService,
                           QuizConfig quizConfig,
                           QuizMessageSource quizMessageSource,
                           QuestionPrinter questionPrinter,
                           IOService ioService) {
        this.questionService = questionService;
        this.userInteractionService = userInteractionService;
        this.quizConfig = quizConfig;
        this.quizMessageSource = quizMessageSource;
        this.questionPrinter = questionPrinter;
        this.ioService = ioService;
    }

    @Override
    public void registerUser() {
        //попросим пользователя представиться
        String userNameString = userInteractionService.askUserForString(
                quizMessageSource.getMessage("strings.greetings"));
        if (userNameString != null) {
            this.user = new User(userNameString);
        }
    }

    @Override
    public void startQuiz() {
        if (this.user == null) {
            registerUser();
        }
        //начинаем игру
        userInteractionService.promptUser(quizMessageSource.getMessage(
                "strings.hello", user.getUserName(), String.valueOf(quizConfig.getQuiz().getScoreToWin())));
        //В цикле зададим вопросы и соберем ответы
        while (questionService.hasNext()) {
            Question currentQuestion = questionService.getNextQuestion();
            questionPrinter.printQuestion(currentQuestion);
            if (currentQuestion.needTextAnswer()) {
                String userAnswerString = userInteractionService.askUserForString(quizMessageSource.getMessage("strings.enter-answer-text"));
                if (currentQuestion.checkResponseString(userAnswerString)) {
                    user.upScore();
                }
            } else {
                int userAnswer = userInteractionService.askUserForInt(quizMessageSource.getMessage("strings.enter-answer-number"));
                if (currentQuestion.checkResponseIndex(userAnswer)) {
                    user.upScore();
                }
            }
            countOfAskedQuestions++;
        }
        //возьмем паузу на подсчет очков
        userInteractionService.promptUser(quizMessageSource.getMessage("strings.let-me-think"));
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            userInteractionService.promptUser("...");
        }

        int resultPercent = 100 / countOfAskedQuestions * user.getScore();

        if (user.getScore() >= quizConfig.getQuiz().getScoreToWin()) {
            System.out.print(quizMessageSource.getMessage("strings.win", String.valueOf(resultPercent)));
        } else {
            System.out.print(quizMessageSource.getMessage("strings.loose", String.valueOf(resultPercent)));
        }
    }

    public void printAllQuestions() {
        while (questionService.hasNext()) {
            ioService.print(String.valueOf(questionService.getNextQuestion()));
            ioService.println("----------------------------------------");
        }
    }
}
