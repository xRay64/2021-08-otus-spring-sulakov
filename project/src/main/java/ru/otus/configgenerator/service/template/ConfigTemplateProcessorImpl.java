package ru.otus.configgenerator.service.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.configgenerator.config.template.ConfigTemplateProcessorParams;
import ru.otus.configgenerator.service.template.ext.ConfigFileReader;
import ru.otus.configgenerator.service.template.ext.ConfigFileWriter;
import ru.otus.configgenerator.service.template.ext.ConfigFileZipper;
import ru.otus.configgenerator.service.template.ext.PlaceholderReplacer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConfigTemplateProcessorImpl implements ConfigTemplateProcessor {
    private final ConfigFileReader configFileReader;
    private final ConfigFileWriter configFileWriter;
    private final ConfigFileZipper configFileZipper;
    private final PlaceholderReplacer placeholderReplacer;
    private final ConfigTemplateProcessorParams params;

    @Override
    public File getProcessedZip(Map<String, String> userValues) {
        List<File> filesWithValues = new ArrayList<>();
        List<String> templatesToBeProcessed = params.getFilePathsList();
        for (String templateFilePath : templatesToBeProcessed) {
            ArrayList<String> templateFileStrings = configFileReader.readFileAsArrayList(templateFilePath);
            List<String> replacedFileStrings = placeholderReplacer.replacePlaceHolderByValue(templateFileStrings, userValues);
            File file = configFileWriter.writeListOfStringsToFile(replacedFileStrings, getFileNameByTemplatePath(templateFilePath));
            filesWithValues.add(file);
        }

        return configFileZipper.zipFiles(filesWithValues);
    }

    private String getFileNameByTemplatePath(String templateFilePath) {
        int indexOfLastSlash = templateFilePath.lastIndexOf('/');
        return indexOfLastSlash > 0 ? templateFilePath.substring(indexOfLastSlash + 1) : templateFilePath;
    }
}
