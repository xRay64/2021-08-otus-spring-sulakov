package ru.otus.configgenerator.service.template.ext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = {PlaceholderReplacerImpl.class, ConfigFileReaderImpl.class})
@DisplayName("PlaceholderReplacerImpl должен")
class PlaceholderReplacerTest {

    @Autowired
    private PlaceholderReplacer placeholderReplacer;
    @Autowired
    private ConfigFileReader configFileReader;

    @Test
    @DisplayName("заменять плэйсхолдер на значение")
    void shouldReplacePlaceHolderByValue() {
        ArrayList<String> strings = configFileReader.readFileAsArrayList("build.gradle.template");

        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("mavenRepositoryURL", "http://test.maven.ru/");
        valuesMap.put("pluginVersion", "0.0.1");
        valuesMap.put("projectName", "test project");
        valuesMap.put("systemName", "TEST_SYSTEM");
        valuesMap.put("schemasList", "schema1, schema2");

        List<String> replacePlaceHolderByValue = placeholderReplacer.replacePlaceHolderByValue(strings, valuesMap);
        replacePlaceHolderByValue.forEach(System.out::println);
    }
}