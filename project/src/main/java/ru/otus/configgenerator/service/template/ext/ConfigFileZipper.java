package ru.otus.configgenerator.service.template.ext;

import java.io.File;
import java.util.List;

public interface ConfigFileZipper {
    File zipFiles(List<File> fileList);
}
