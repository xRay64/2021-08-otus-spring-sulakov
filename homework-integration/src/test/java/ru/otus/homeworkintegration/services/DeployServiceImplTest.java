package ru.otus.homeworkintegration.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homeworkintegration.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DeployServiceImpl должен")
class DeployServiceImplTest {
    private final DeployService deployService = new DeployServiceImpl();

    @Test
    @DisplayName("деплоить артефакты")
    void shouldDoDeploy() {
        Build testBuild = new Build(
                "TestBuild"
                , List.of(new BuildArtifact(BuildArtifactType.SQL_SCRIPT, "Nest SQL_script build"))
        );
        String dbDeployResult = deployService.deploy(List.of(testBuild));

        assertThat(dbDeployResult)
                .isEqualTo("Deployed:\nTestBuild\n");
    }
}