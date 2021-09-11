package ru.otus.homework02.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.quiz")
@Component
public class QuizConfig {
    private int scoreToWin = 5;

    public int getScoreToWin() {
        return scoreToWin;
    }

    public void setScoreToWin(int scoreToWin) {
        this.scoreToWin = scoreToWin;
    }
}
