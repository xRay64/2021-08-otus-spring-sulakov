package ru.otus.homework01.service;

import ru.otus.homework01.dao.QuizData;

public class QuizServiceImpl implements QuizService {
    private final QuizData quizData;

    public QuizServiceImpl(QuizData quizData) {
        this.quizData = quizData;
    }

    public void printAllQuestions() {
        while (quizData.hasNext()) {
            System.out.print(quizData.getNextQuestion());
            System.out.println("----------------------------------------");
        }
    }
}
