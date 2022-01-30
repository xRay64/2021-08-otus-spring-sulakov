package ru.otus.homeworkintegration.services;

import org.springframework.stereotype.Service;
import ru.otus.homeworkintegration.domain.Build;
import ru.otus.homeworkintegration.domain.BuildArtifact;

import java.util.List;

@Service
public class DeployServiceImpl implements DeployService {
    @Override
    public String deploy(List<Build> buildList) {
        StringBuilder deployedArtifactsString = new StringBuilder();
        for (Build build : buildList) {
            deployedArtifactsString
                    .append(build.getBuildName())
                    .append(" With artefacts:\n");
            for (BuildArtifact buildArtifact : build.getBuildArtifacts()) {
                deployedArtifactsString
                        .append(" ")
                        .append(buildArtifact.getArtifact())
                        .append("\n");
            }
        }
        return String.format("Deployed:\n%s", deployedArtifactsString);
    }
}
