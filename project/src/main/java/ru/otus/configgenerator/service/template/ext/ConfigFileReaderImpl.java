package ru.otus.configgenerator.service.template.ext;

import org.springframework.stereotype.Service;
import ru.otus.configgenerator.exception.ConfigGeneratorException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ConfigFileReaderImpl implements ConfigFileReader {

    @Override
    public ArrayList<String> readFileAsArrayList(String templateFilePath) {
        ArrayList<String> fileStrings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(templateFilePath))) {
            while (reader.ready()) {
                fileStrings.add(reader.readLine());
            }
        } catch (IOException e) {
            throw new ConfigGeneratorException("Error while reading template file", e);
        }

        return fileStrings;
    }
}
