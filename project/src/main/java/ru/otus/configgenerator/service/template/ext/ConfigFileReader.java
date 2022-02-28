package ru.otus.configgenerator.service.template.ext;

import java.util.ArrayList;

public interface ConfigFileReader {
    ArrayList<String> readFileAsArrayList(String templateFilePath);
}
