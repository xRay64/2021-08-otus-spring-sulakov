package ru.otus.configgenerator.service.repository;

import ru.otus.configgenerator.model.repository.Branch;
import ru.otus.configgenerator.model.repository.RepositoryDirectory;

import java.util.List;

public interface GitRepositoryReader {
    List<Branch> getBranchListFor(String repositoryLink);
    List<RepositoryDirectory> getDirectories(String repositoryUrl, Branch branch);
}
