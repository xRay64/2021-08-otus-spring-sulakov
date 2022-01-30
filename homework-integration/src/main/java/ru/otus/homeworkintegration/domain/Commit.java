package ru.otus.homeworkintegration.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Commit {
    private String commitId;
    private final List<VCSChanges> vcsChanges;
}
