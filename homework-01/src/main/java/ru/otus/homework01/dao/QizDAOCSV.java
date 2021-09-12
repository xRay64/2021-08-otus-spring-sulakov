package ru.otus.homework01.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

@PropertySource("classpath:application.properties")
@Component
public class QizDAOCSV implements QuizDAO {

    private final String fileName;
    private ArrayList<String> strings;

    public QizDAOCSV(@Value("${csv.filename}") String fileName) {
        this.fileName = fileName;
    }

    private void readFile() {
        if (fileName == null) {
            throw new QuizDAOException("Filename is NULL");
        } else {
            //Читаем файл в список и закрываем поток
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName)))) {
                strings = new ArrayList<>();
                while (reader.ready()) {
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

    @Override
    public int getStringsCount() {
        lazyLoad();
        return strings.size();
    }

    @Override
    public String getString(int index) {
        lazyLoad();
        if (index > strings.size() - 1 || index < 0) {
            throw new QuizDAOException("Index out of bound");
        }
        return strings.get(index);
    }
}
