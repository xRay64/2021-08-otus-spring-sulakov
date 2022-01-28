package ru.otus.homeworkintegration.services;

import ru.otus.homeworkintegration.domain.Commit;

import java.util.List;

public interface GitLab {
    List<Commit> getChanges(int countOfChanges);
}
