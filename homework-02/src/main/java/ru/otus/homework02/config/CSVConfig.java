package ru.otus.homework02.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application")
@Configuration
public class CSVConfig {

    private CSV csv;

    public void setCsv(CSV csv) {
        this.csv = csv;
    }

    public CSV getCsv() {
        return csv;
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
}
