package ru.otus.configgenerator.service.template.ext;

import org.springframework.stereotype.Service;
import ru.otus.configgenerator.exception.ConfigGeneratorException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class ConfigFileWriterImpl implements ConfigFileWriter {

    @Override
    public File writeListOfStringsToFile(List<String> fileStrings, String fileName) {
        try {
            File tmpFile = File.createTempFile(fileName, "");
            FileWriter fileWriter = new FileWriter(tmpFile);
            for (String fileString : fileStrings) {
                fileWriter.write(fileString + "\n");
            }
            fileWriter.close();

            return tmpFile;
        } catch (IOException e) {
            throw new ConfigGeneratorException("Error while writing file", e);
        }
    }
}
