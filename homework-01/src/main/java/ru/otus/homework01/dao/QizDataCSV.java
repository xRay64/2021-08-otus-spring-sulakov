package ru.otus.homework01.dao;

import ru.otus.homework01.domain.Question;
import ru.otus.homework01.helper.StringParser;

import java.io.*;

public class QizDataCSV implements QuizData{

    private final BufferedReader reader;

    public QizDataCSV(String fileName) {
        this.reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)));
    }

    @Override
    public boolean hasNext(){
        boolean res = false;
        try {
            res = reader.ready();
        } catch (IOException e) {
            System.out.println("Error in hasNext");
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Question getNextQuestion() {
        Question resQuestion = null;
        try {
            resQuestion = StringParser.parseStringToQuestion(reader.readLine());
        } catch (IOException e) {
            System.out.println("Error in getNextQuestion");
            e.printStackTrace();
        }
        return resQuestion;
    }
}
