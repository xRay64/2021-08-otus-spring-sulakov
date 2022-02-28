package ru.otus.configgenerator.service.template;

import java.io.File;
import java.util.Map;

public interface ConfigTemplateProcessor {
    File getProcessedZip(Map<String, String> userValues);
}
