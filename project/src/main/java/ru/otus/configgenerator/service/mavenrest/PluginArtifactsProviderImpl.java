package ru.otus.configgenerator.service.mavenrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.configgenerator.config.mavenrest.PluginArtifactProviderParams;
import ru.otus.configgenerator.exception.ConfigGeneratorException;
import ru.otus.configgenerator.model.mavenRest.ArtifactList;
import ru.otus.configgenerator.model.mavenRest.PluginItem;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class PluginArtifactsProviderImpl implements PluginArtifactProvider {
    private static final Logger log = LoggerFactory.getLogger(PluginArtifactsProviderImpl.class);

    private final String nexusRepoHost;
    private final String searchApiPath;
    private final String searchParameterName;
    private final String artifactName;

    private final RestOperations rest;

    public PluginArtifactsProviderImpl(PluginArtifactProviderParams params, RestOperations rest) {
        this.nexusRepoHost = params.getNexusRepoHost();
        this.searchApiPath = params.getSearchApiPath();
        this.searchParameterName = params.getSearchParameterName();
        this.artifactName = params.getArtifactName();
        this.rest = rest;
    }

    @Override
    @Cacheable(value = "mavenPluginList")
    public List<PluginItem> getPluginList() {
        List<PluginItem> result;
        var request = getRequestEntity();
        ResponseEntity<ArtifactList> response = rest.exchange(
                request,
                new ParameterizedTypeReference<>() {}
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            Optional<ArtifactList> responseBody = Optional.ofNullable(response.getBody());
            result = responseBody
                    .orElseThrow(() -> new ConfigGeneratorException("Response of plugin versions is empty"))
                    .getItems();
        } else {
            throw new ConfigGeneratorException(
                    "Error while getting info from Nexus. Http status is " + response.getStatusCode()
            );
        }
        return result;
    }

    private RequestEntity getRequestEntity() {
        return new RequestEntity(
                HttpMethod.GET,
                getSearchApiUriFor(artifactName)
        );
    }

    private URI getSearchApiUriFor(String artifactName) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(nexusRepoHost)
                .path(searchApiPath)
                .queryParamIfPresent(searchParameterName, Optional.ofNullable(artifactName))
                .build();

        log.info("Creating URI for " + uriComponents.toString());
        return uriComponents.toUri();
    }
}
