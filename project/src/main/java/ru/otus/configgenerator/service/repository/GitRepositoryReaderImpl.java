package ru.otus.configgenerator.service.repository;

import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.lib.Ref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.configgenerator.exception.ConfigGeneratorException;
import ru.otus.configgenerator.model.repository.Branch;
import ru.otus.configgenerator.model.repository.RepositoryDirectory;
import ru.otus.configgenerator.service.repository.ext.RepositoryFoldersFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GitRepositoryReaderImpl implements GitRepositoryReader {
    private final static Logger log = LoggerFactory.getLogger(GitRepositoryReaderImpl.class);

    private final RepositoryFoldersFilter repositoryFoldersFilter;

    @Override
    public List<Branch> getBranchListFor(String repositoryLink) {
        List<Branch> result = new ArrayList<>();

        Git clonedTempRepository = getClonedTempRepository(repositoryLink);

        try {
            for (Ref ref : clonedTempRepository.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call()) {
                Branch newBranch = new Branch();
                newBranch.setName(ref.getName());
                result.add(newBranch);
            }
        } catch (GitAPIException e) {
            throw new ConfigGeneratorException("API error while getting refs", e);
        }

        return result;

    }

    @Override
    public List<RepositoryDirectory> getDirectories(String repositoryUrl, Branch branch) {
        List<RepositoryDirectory> result = new ArrayList<>();
        Git clonedTempRepository = getClonedTempRepository(repositoryUrl);
        try {
            clonedTempRepository.checkout().setName(branch.getName()).call();
        } catch (RefNotFoundException e) {
            throw new ConfigGeneratorException("Ref not found", e);
        } catch (RefAlreadyExistsException e) {
            throw new ConfigGeneratorException("Ref already exists", e);
        } catch (InvalidRefNameException e) {
            throw new ConfigGeneratorException("Invalid ref name", e);
        } catch (CheckoutConflictException e) {
            throw new ConfigGeneratorException("Checkout conflict", e);
        } catch (GitAPIException e) {
            throw new ConfigGeneratorException("API error", e);
        }

        File gitDirectory = clonedTempRepository.getRepository().getDirectory();
        File repository = gitDirectory.getParentFile();
        File[] files = repository.listFiles();
        if (files == null || files.length == 0) {
            throw new ConfigGeneratorException("Repository directory is empty");
        }
        for (File file : files) {

            if (file.isDirectory()
                    && !file.getName().startsWith(".")
                    && !isEqualsAnyIn(repositoryFoldersFilter.getFilteredFoldersNames(), file.getName())
            ) {
                RepositoryDirectory repositoryDirectory = new RepositoryDirectory();
                repositoryDirectory.setName(file.getName());
                result.add(repositoryDirectory);
            }
        }
        return result;
    }

    private Git getClonedTempRepository(String repositoryLink) {
        // prepare a new folder for the cloned repository
        File localPath = null;
        try {
            localPath = File.createTempFile("TestGitRepository" + UUID.randomUUID().toString(), "");
        } catch (IOException e) {
            throw new ConfigGeneratorException("Error while creating temp repository directory", e);
        }

        if (!localPath.delete()) {
            throw new ConfigGeneratorException("Could not delete temporary file " + localPath);
        }

        // then clone
        log.info("Cloning from " + repositoryLink + " to " + localPath);
        try (Git git = Git.cloneRepository()
                .setURI(repositoryLink)
                .setDirectory(localPath)
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            log.info("Having repository: " + git.getRepository().getDirectory());

            return git;
        } catch (InvalidRemoteException e) {
            throw new ConfigGeneratorException("Error while cloning repository", e);
        } catch (TransportException e) {
            throw new ConfigGeneratorException("Transport error while cloning repository", e);
        } catch (GitAPIException e) {
            throw new ConfigGeneratorException("API error while cloning repository", e);
        }
    }

    private boolean isEqualsAnyIn(String[] stringArray, String name) {
        for (String s : stringArray) {
            if (name.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
