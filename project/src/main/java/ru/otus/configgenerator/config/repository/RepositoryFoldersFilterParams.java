package ru.otus.configgenerator.config.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.repository-folders-filter")
@Configuration
@Getter
@Setter
public class RepositoryFoldersFilterParams {
    private String filteredFolders;
}
