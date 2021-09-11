package ru.otus.homework02.service;

import org.springframework.stereotype.Service;
import ru.otus.homework02.dao.QuizDAO;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.helper.StringParser;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuizDAO quizDAO;
    private final StringParser stringParser;
    private int currentQuestionPointer = 0;

    public QuestionServiceImpl(QuizDAO quizDAO, StringParser stringParser) {
        this.quizDAO = quizDAO;
        this.stringParser = stringParser;
    }

    @Override
    public boolean hasNext() {
        return currentQuestionPointer < quizDAO.getStringsCount();
    }

    @Override
    public Question getNextQuestion() {
        Question result = stringParser.parseStringToQuestion(quizDAO.getString(currentQuestionPointer));
        currentQuestionPointer++;
        return result;
    }
}
