package ru.otus.configgenerator.controllers;

import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.configgenerator.model.repository.Branch;
import ru.otus.configgenerator.model.repository.RepositoryDirectory;
import ru.otus.configgenerator.service.repository.GitRepositoryReader;
import ru.otus.configgenerator.service.template.ConfigTemplateProcessor;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RepositoryContentController {
    private final GitRepositoryReader gitRepositoryReader;
    private final ConfigTemplateProcessor configTemplateProcessor;

    @GetMapping("/repository/branches")
    public List<Branch> getRepositoryContent(@RequestParam("repositoryUrl") String repositoryUrl) {
        return gitRepositoryReader.getBranchListFor(repositoryUrl);
    }

    @GetMapping("/repository/branchContent")
    public List<RepositoryDirectory> getBranchContent(
            @RequestParam("branchName") String branchName,
            @RequestParam("repositoryUrl") String repositoryUrl
            ) {
        Branch branch = new Branch();
        branch.setName(branchName);
        return gitRepositoryReader.getDirectories(repositoryUrl, branch);
    }
}
