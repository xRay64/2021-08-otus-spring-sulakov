package ru.otus.configgenerator.service.template.ext;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PlaceholderReplacerImpl implements PlaceholderReplacer {
    @Override
    public List<String> replacePlaceHolderByValue(List<String> fileStrings, Map<String, String> values) {
        List<String> resultFileStrings = new ArrayList<>();
        StringSubstitutor stringSubstitutor = new StringSubstitutor(values);
        for (String sourceFileString : fileStrings) {
            resultFileStrings.add(stringSubstitutor.replace(sourceFileString));
        }
        return resultFileStrings;
    }
}
