package ru.otus.configgenerator.service.template.ext;

import java.util.List;
import java.util.Map;

public interface PlaceholderReplacer {
    List<String> replacePlaceHolderByValue(List<String> fileStrings, Map<String, String> values);
}
