package ru.otus.configgenerator.config.template;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "application.template-processor")
@Configuration
@Getter
@Setter
public class ConfigTemplateProcessorParams {
    private String templatePath;
    private List<String> templateList;

    public List<String> getFilePathsList() {
        return templateList.stream().map(fileName -> addEndingSlashIfNeeded(templatePath) + fileName).toList();
    }

    private String addEndingSlashIfNeeded(String s) {
        return s.endsWith("/") ? s : s + "/";
    }
}