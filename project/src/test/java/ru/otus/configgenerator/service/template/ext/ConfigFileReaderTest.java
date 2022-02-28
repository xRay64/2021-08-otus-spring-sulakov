package ru.otus.configgenerator.service.template.ext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;


@SpringBootTest(classes = {ConfigFileReaderImpl.class})
class ConfigFileReaderTest {

    @Autowired
    ConfigFileReader configFileReader;

    @Test
    void process() {
        ArrayList<String> strings = configFileReader.readFileAsArrayList("build.gradle.template");
        strings.forEach(System.out::println);
    }
}