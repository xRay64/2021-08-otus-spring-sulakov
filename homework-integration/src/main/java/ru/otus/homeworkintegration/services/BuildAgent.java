package ru.otus.homeworkintegration.services;

import ru.otus.homeworkintegration.domain.Build;
import ru.otus.homeworkintegration.domain.Commit;

public interface BuildAgent {
    Build build(Commit commit);
}
