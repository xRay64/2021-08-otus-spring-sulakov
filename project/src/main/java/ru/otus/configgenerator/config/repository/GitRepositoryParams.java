package ru.otus.configgenerator.config.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.git-repository-reader")
@Configuration
@Getter
@Setter
public class GitRepositoryParams {
    private String repositoryUrl;
    private String repositoryHost;
    private String hostApiPath;
    private String apiBranchesSubPath;
    private String queryTokeParamName;
    private String accessToken;
}
