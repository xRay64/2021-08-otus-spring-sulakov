package ru.otus.configgenerator.service.repository.ext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.configgenerator.config.repository.RepositoryFoldersFilterParams;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {RepositoryFoldersFilterImpl.class})
@EnableConfigurationProperties(RepositoryFoldersFilterParams.class)
class RepositoryFoldersFilterTest {

    @Autowired
    RepositoryFoldersFilter repositoryFoldersFilter;

    @Test
    void getFilteredFoldersNames() {
        String[] filteredFoldersNames = repositoryFoldersFilter.getFilteredFoldersNames();
        for (String filteredFoldersName : filteredFoldersNames) {
            System.out.println(filteredFoldersName);
        }

        assertThat(filteredFoldersNames)
                .isNotNull()
                .hasSize(3)
                .containsAll(List.of("folder1", "folder2", "template"));
    }
}