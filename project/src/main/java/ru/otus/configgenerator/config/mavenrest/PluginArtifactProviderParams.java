package ru.otus.configgenerator.config.mavenrest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "application.plugin-artifact-provider")
@Configuration
@Getter
@Setter
public class PluginArtifactProviderParams {
    private String nexusRepoHost;
    private String searchApiPath;
    private String searchParameterName;
    private String artifactName;
}
