package ru.otus.homeworkintegration.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BuildArtifact {
    private final BuildArtifactType artifactType;
    private final String artifact;
}
