package ru.otus.homeworkintegration.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class VCSChanges {
    private final VCSChangesType changesType;
}
