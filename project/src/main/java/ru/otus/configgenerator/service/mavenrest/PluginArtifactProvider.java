package ru.otus.configgenerator.service.mavenrest;

import ru.otus.configgenerator.model.mavenRest.PluginItem;

import java.util.List;

public interface PluginArtifactProvider {
    List<PluginItem> getPluginList();
}
