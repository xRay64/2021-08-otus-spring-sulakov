package ru.otus.homework02.helper;

import org.springframework.stereotype.Component;
import ru.otus.homework02.config.QuizConfig;
import ru.otus.homework02.domain.Question;
import ru.otus.homework02.service.QuizMessageSource;

import java.util.Map;

@Component
public class QuestionPrinterImpl implements QuestionPrinter {
    private final QuizConfig quizConfig;
    private final QuizMessageSource quizMessageSource;
    private final IOService ioService;

    public QuestionPrinterImpl(QuizConfig quizConfig, QuizMessageSource quizMessageSource, IOService ioService) {
        this.quizConfig = quizConfig;
        this.quizMessageSource = quizMessageSource;
        this.ioService = ioService;
    }

    @Override
    public void printQuestion(Question question) {
        Map<Integer, String> answersToIterate;
        StringBuilder questionAsString = new StringBuilder();
        questionAsString.append(quizMessageSource.getMessage("string.question.is"));
        if (quizConfig.getLanguage().contains("RU")) {
            questionAsString.append(question.getQuestionTextTranslated());
        } else {
            questionAsString.append(question.getQuestionText());
        }
        questionAsString.append("\n");
        if (question.getResponses().size() > 0) {
            questionAsString.append(quizMessageSource.getMessage("string.question.responses"));
            if (quizConfig.getLanguage().contains("RU")) {
                answersToIterate = question.getResponsesTranslated();
            } else {
                answersToIterate = question.getResponses();
            }
            for (Map.Entry<Integer, String> responsePair : answersToIterate.entrySet()) {
                questionAsString.append(String.format("%d. %s", responsePair.getKey(), responsePair.getValue()));
                questionAsString.append("\n");
            }
        }
        ioService.print(questionAsString.toString());
    }
}
