package ru.otus.configgenerator.service.repository;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.configgenerator.config.repository.GitRepositoryParams;
import ru.otus.configgenerator.exception.ConfigGeneratorException;
import ru.otus.configgenerator.model.repository.Branch;
import ru.otus.configgenerator.model.repository.RepositoryDirectory;

import java.util.List;
import java.util.Optional;

//@Service
public class GitRepositoryReaderRestImpl implements GitRepositoryReader {
    private static final String PER_PAGE_QUERY_PARAM_NAME = "per_page";
    private static final int PER_PAGE_QUERY_PARAM_VALUE = 100;

    private final RestTemplate rest;
    private final String repositoryHost;
    private final String hostApiPath;
    private final String apiBranchesSubPath;
    private final String queryTokeParamName;
    private final String accessToken;

    public GitRepositoryReaderRestImpl(RestTemplate rest, GitRepositoryParams params) {
        this.rest = rest;
        this.repositoryHost = params.getRepositoryHost();
        this.hostApiPath = params.getHostApiPath();
        this.apiBranchesSubPath = params.getApiBranchesSubPath();
        this.queryTokeParamName = params.getQueryTokeParamName();
        this.accessToken = params.getAccessToken();
    }

    @Override
    public List<Branch> getBranchListFor(String repositoryLink) {
        List<Branch> result;
        String projectName = getProjectNameFrom(repositoryLink)
                .orElseThrow(
                        () -> new ConfigGeneratorException("Error while parsing project name from " + repositoryLink)
                );
        ResponseEntity<List<Branch>> response = doBranchRequest(getApiUri(), projectName);
        Optional<List<Branch>> responseBody = Optional.ofNullable(response.getBody());
        result = responseBody
                .orElseThrow(() -> new ConfigGeneratorException("Response of plugin versions is empty"));

        return result;
    }

    @Override
    public List<RepositoryDirectory> getDirectories(String repositoryUrl, Branch branch) {
        return null;
    }

    private String getApiUri() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(repositoryHost)
                .pathSegment(hostApiPath, "{projectName}", apiBranchesSubPath)
                .queryParam(queryTokeParamName, accessToken)
                .queryParam(PER_PAGE_QUERY_PARAM_NAME, PER_PAGE_QUERY_PARAM_VALUE)
                .build();

        return uriComponents.toString();
    }

    private ResponseEntity<List<Branch>> doBranchRequest(String repositoryLink, @Nullable String uriParams) {

        ResponseEntity<List<Branch>> response = rest.exchange(
                repositoryLink,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {},
                uriParams
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            return response;
        } else {
            throw new ConfigGeneratorException(
                    "Error while getting info from Nexus. Http status is " + response.getStatusCode()
            );
        }
    }

    private Optional<String> getProjectNameFrom(String repositoryLink) {
        if (repositoryLink.startsWith("git")) {
            return Optional.of(repositoryLink.replaceAll("(.*:)(.*)(\\.git)", "$2"));
        } else if (repositoryLink.startsWith("https")) {
            return Optional.of(repositoryLink.replaceAll("(^https://[a-zA-Z.]*/)(.*)(\\.git$)", "$2"));
        }

        return Optional.empty();
    }
}
