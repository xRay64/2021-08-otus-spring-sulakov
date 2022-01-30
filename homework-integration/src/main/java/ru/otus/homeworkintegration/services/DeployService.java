package ru.otus.homeworkintegration.services;

import ru.otus.homeworkintegration.domain.Build;

import java.util.List;

public interface DeployService {
    String deploy(List<Build> build);
}
