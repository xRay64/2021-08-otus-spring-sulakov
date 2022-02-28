package ru.otus.configgenerator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.otus.configgenerator.exception.ConfigGeneratorException;
import ru.otus.configgenerator.service.mavenrest.PluginArtifactProvider;
import ru.otus.configgenerator.service.template.ConfigTemplateProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ConfigController {
    private final PluginArtifactProvider pluginArtifactProvider;
    private final ConfigTemplateProcessor configTemplateProcessor;

    @GetMapping("/")
    public String getConfig(Model model) {
        model.addAttribute("pluginList", pluginArtifactProvider.getPluginList());
        return "index";
    }

    @GetMapping("/process")
    @ResponseBody
    public byte[] getGeneratedConfigs(
            @RequestParam("pluginVersion") String pluginVersion,
            @RequestParam("pluginRepository") String pluginRepository,
            @RequestParam("projectName") String projectName,
            @RequestParam("systemName") String systemName,
            @RequestParam("schemasNames") String schemasNames
    ) throws FileNotFoundException {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mavenRepositoryURL", pluginRepository.trim());
        paramsMap.put("pluginVersion", pluginVersion.trim());
        paramsMap.put("projectName", projectName.trim());
        paramsMap.put("systemName", systemName.trim());
        paramsMap.put("schemasList", schemasNames.trim());
        InputStream is = new FileInputStream(configTemplateProcessor.getProcessedZip(paramsMap));
        byte[] bytes;
        try {
            bytes = is.readAllBytes();
        } catch (IOException e) {
            throw new ConfigGeneratorException("Error reading result archive", e);
        }
        return bytes;
    }
}
