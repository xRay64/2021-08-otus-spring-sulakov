package ru.otus.homeworkintegration.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homeworkintegration.domain.Commit;
import ru.otus.homeworkintegration.domain.VCSChanges;
import ru.otus.homeworkintegration.domain.VCSChangesType;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GitLabImpl должен")
class GitLabImplTest {
    @Test
    @DisplayName("возвращать список коммитов")
    public void shouldGiveListOfCommits() {
        GitLab gitLab = new GitLabImpl();
        List<Commit> changes = gitLab.getChanges(3);

        System.out.println(changes.toString());

        assertThat(changes)
                .hasSize(3);

        List<VCSChanges> commitVCSChanges = changes.get(0).getVcsChanges();

        assertThat(commitVCSChanges)
                .extracting(VCSChanges::getChangesType)
                .containsAnyElementsOf(Arrays.stream(VCSChangesType.values()).toList());
    }
}