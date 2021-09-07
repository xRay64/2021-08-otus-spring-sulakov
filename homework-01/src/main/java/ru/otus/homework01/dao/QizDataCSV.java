package ru.otus.homework01.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.homework01.domain.Question;
import ru.otus.homework01.helper.StringParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@PropertySource("classpath:application.properties")
@Component
public class QizDataCSV implements QuizData {

    private final String fileName;
    private final StringParser stringParser;
    private List<String> dataList = new ArrayList<>();
    private int currentDataRow = 0;

    public QizDataCSV(@Value("${csv.filename}") String fileName, StringParser stringParser) {
        this.fileName = fileName;
        this.stringParser = stringParser;
    }

    @Override
    public void prepareData() {
        Stream<String> stringStream = null;
        try {
            stringStream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()));
        } catch (IOException | URISyntaxException e) {
            System.out.println("Error in prepareData");
            e.printStackTrace();
        }
        if (stringStream != null) {
            stringStream.forEach(s -> dataList.add(s));
            stringStream.close();
        } else {
            System.out.println("Error while parse a csv");
        }
    }

    @Override
    public boolean hasNext() {
        return currentDataRow < dataList.size();
    }

    @Override
    public Question getNextQuestion() {
        Question question = stringParser.parseStringToQuestion(dataList.get(currentDataRow));
        currentDataRow++;
        return question;
    }
}
