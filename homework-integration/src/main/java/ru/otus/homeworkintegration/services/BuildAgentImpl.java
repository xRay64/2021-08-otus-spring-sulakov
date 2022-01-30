package ru.otus.homeworkintegration.services;

import org.springframework.stereotype.Component;
import ru.otus.homeworkintegration.domain.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuildAgentImpl implements BuildAgent {

    @Override
    public Build build(Commit commit) {
        List<BuildArtifact> artifacts = new ArrayList<>();
        for (VCSChanges vcsChange : commit.getVcsChanges()) {
            artifacts.add(buildArtifacts(vcsChange));
        }
        return new Build("Build from commit " + commit.getCommitId(), artifacts);
    }

    private BuildArtifact buildArtifacts(VCSChanges changes) {
        BuildArtifactType artifactType = null;
        switch (changes.getChangesType()) {
            case JAVA -> artifactType = BuildArtifactType.DOCKER_IMAGE;
            case DB -> artifactType = BuildArtifactType.SQL_SCRIPT;
            case CPP -> artifactType = BuildArtifactType.EXE;
            case PYTHON -> artifactType = BuildArtifactType.SH_SCRIPT;
        }
        if (artifactType != null) {
            return new BuildArtifact(artifactType, String.format("The build artefact of type: %s", artifactType));
        } else {
            throw new RuntimeException("Could not define artifact type");
        }
    }
}
