package ru.otus.homeworkintegration.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homeworkintegration.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BuildAgentImpl должен")
class BuildAgentImplTest {
    private static final VCSChangesType JAVA_CHANGES = VCSChangesType.JAVA;
    private static final VCSChangesType DB_CHANGES = VCSChangesType.DB;
    private final BuildAgent buildAgent = new BuildAgentImpl();


    @Test
    @DisplayName("возвращать артефакт сборки по типу изменений")
    public void shouldReturnArtifactDependsOnChangeType() {
        Commit testJavaCommit = new Commit("Test java commit", List.of(new VCSChanges(JAVA_CHANGES)));
        Commit testDbCommit = new Commit("Test DB commit", List.of(new VCSChanges(DB_CHANGES)));
        Build javaBuild = buildAgent.build(testJavaCommit);
        Build dbBuild = buildAgent.build(testDbCommit);

        assertThat(javaBuild)
                .extracting(Build::getBuildName)
                .isEqualTo("Build from commit Test java commit");
        assertThat(javaBuild.getBuildArtifacts())
                .hasSize(1)
                .first()
                .isInstanceOf(BuildArtifact.class);

        assertThat(dbBuild)
                .extracting(Build::getBuildName)
                .isEqualTo("Build from commit Test DB commit");
        assertThat(dbBuild.getBuildArtifacts())
                .hasSize(1)
                .first()
                .isInstanceOf(BuildArtifact.class)
                .usingRecursiveComparison()
                .isEqualTo(new BuildArtifact(BuildArtifactType.SQL_SCRIPT, "The build artefact of type: SQL_SCRIPT"));
    }
}