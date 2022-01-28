package ru.otus.homeworkintegration.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class Build {
    private final String buildName;
    private final List<BuildArtifact> buildArtifacts;
}
