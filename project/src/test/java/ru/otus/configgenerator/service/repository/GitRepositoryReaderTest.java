package ru.otus.configgenerator.service.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.configgenerator.config.repository.GitRepositoryParams;
import ru.otus.configgenerator.config.repository.RepositoryFoldersFilterParams;
import ru.otus.configgenerator.model.repository.Branch;
import ru.otus.configgenerator.model.repository.RepositoryDirectory;
import ru.otus.configgenerator.service.repository.ext.RepositoryFoldersFilterImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {GitRepositoryReaderImpl.class, RepositoryFoldersFilterImpl.class})
@EnableConfigurationProperties({GitRepositoryParams.class, RepositoryFoldersFilterParams.class})
@DisplayName("GitRepositoryReader should")
class GitRepositoryReaderTest {

    @Autowired
    private GitRepositoryReader gitRepositoryReader;

    @Test
    @DisplayName("return branches")
    void doRemote() {
        List<Branch> branchList = gitRepositoryReader
                .getBranchListFor("https://github.com/xRay64/otus_database_repo.git");

        System.out.println("branchList size = " + branchList.size() );

        for (Branch branch : branchList) {
            System.out.println(branch.toString());
        }

        assertThat(branchList)
                .isNotNull();
    }

    @Test
    @DisplayName("return directories list")
    void list() {
        Branch branch = new Branch();
        branch.setName("refs/remotes/origin/feature/test1");
        List<RepositoryDirectory> directories = gitRepositoryReader.getDirectories(
                "https://github.com/xRay64/otus_database_repo.git", branch
        );
        for (RepositoryDirectory directory : directories) {
            System.out.println(directory.getName());
        }

        assertThat(directories)
                .isNotNull();
    }
}