package ru.otus.homework02.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application")
@Configuration
public class AppConfig {

    private CSV csv;
    private Quiz quiz;

    public CSV getCsv() {
        return csv;
    }

    public void setCsv(CSV csv) {
        this.csv = csv;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public static class CSV {
        private String fileName;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }

    public class Quiz {
        private int scoreToWin;

        public int getScoreToWin() {
            return scoreToWin;
        }

        public void setScoreToWin(int scoreToWin) {
            this.scoreToWin = scoreToWin;
        }
    }
}
