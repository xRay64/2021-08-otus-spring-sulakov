package ru.otus.configgenerator.service.template.ext;

import java.io.File;
import java.util.List;

public interface ConfigFileWriter {
    File writeListOfStringsToFile(List<String> fileStrings, String fileName);
}
