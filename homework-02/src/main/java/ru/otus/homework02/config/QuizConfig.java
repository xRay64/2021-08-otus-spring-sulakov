package ru.otus.homework02.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application")
@Configuration
public class QuizConfig {

    private CSV csv;
    private Quiz quiz;
    private String language;

    public void setCsv(CSV csv) {
        this.csv = csv;
    }

    public CSV getCsv() {
        return csv;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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

    public static class Quiz {
        private int scoreToWin = 5;

        public int getScoreToWin() {
            return scoreToWin;
        }

        public void setScoreToWin(int scoreToWin) {
            this.scoreToWin = scoreToWin;
        }
    }
}
