package ru.otus.homework02.service;

import org.springframework.stereotype.Service;
import ru.otus.homework02.dao.QuizDAO;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.helper.StringToQuestionParser;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuizDAO quizDAO;
    private final StringToQuestionParser stringToQuestionParser;
    private int currentQuestionPointer = 0;

    public QuestionServiceImpl(QuizDAO quizDAO, StringToQuestionParser stringToQuestionParser) {
        this.quizDAO = quizDAO;
        this.stringToQuestionParser = stringToQuestionParser;
    }

    @Override
    public boolean hasNext() {
        return currentQuestionPointer < quizDAO.getStringsCount();
    }

    @Override
    public Question getNextQuestion() {
        Question result = stringToQuestionParser.parseStringToQuestion(quizDAO.getString(currentQuestionPointer));
        currentQuestionPointer++;
        return result;
    }
}
