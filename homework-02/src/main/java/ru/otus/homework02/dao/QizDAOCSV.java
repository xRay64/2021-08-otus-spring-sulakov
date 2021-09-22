package ru.otus.homework02.dao;

import org.springframework.stereotype.Component;
import ru.otus.homework02.config.QuizConfig;
import ru.otus.homework02.exceptions.DAOQuizRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class QizDAOCSV implements QuizDAO {

    private final QuizConfig quizConfig;
    private List<String> strings;

    public QizDAOCSV(QuizConfig quizConfig) {
        this.quizConfig = quizConfig;
    }

    @Override
    public int getStringsCount() {
        lazyLoad();
        return strings.size();
    }

    @Override
    public String getString(int index) {
        lazyLoad();
        if (index > strings.size() - 1 || index < 0) {
            throw new DAOQuizRuntimeException("Index out of bound");
        }
        return strings.get(index);
    }

    private void readFile() {
        boolean isFirstRowSkipped = false;
        if (quizConfig.getCsv().getFileName() == null) {
            throw new DAOQuizRuntimeException("Filename is NULL");
        } else {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(quizConfig.getCsv().getFileName());
            if (resourceAsStream == null) {
                throw new DAOQuizRuntimeException("Error while getting stream from resource");
            }
            //Читаем файл в список и закрываем поток
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))) {
                strings = new ArrayList<>();
                while (reader.ready()) {
                    if (!isFirstRowSkipped) { //Пропустим первую строку так как в ней заголовки столбцов
                        reader.readLine();
                        isFirstRowSkipped = true;
                    }
                    strings.add(reader.readLine());
                }
            } catch (IOException e) {
                System.out.println("Error in readFile()");
                e.printStackTrace();
            }
        }
    }

    //читаем файл в том случае, если еще не делали этого
    private void lazyLoad() {
        if (strings == null) {
            readFile();
        }
    }
}
