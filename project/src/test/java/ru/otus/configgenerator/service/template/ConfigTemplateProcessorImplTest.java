package ru.otus.configgenerator.service.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.configgenerator.config.template.ConfigTemplateProcessorParams;
import ru.otus.configgenerator.service.template.ext.*;

import java.io.File;
import java.util.Map;

@SpringBootTest(classes = {
        ConfigTemplateProcessorImpl.class,
        ConfigFileReaderImpl.class,
        ConfigFileWriterImpl.class,
        ConfigFileZipperImpl.class,
        PlaceholderReplacerImpl.class
})
@EnableConfigurationProperties(ConfigTemplateProcessorParams.class)
@DisplayName("ConfigTemplateProcessorImpl должен")
class ConfigTemplateProcessorImplTest {

    @Autowired
    private ConfigTemplateProcessorImpl configTemplateProcessorImpl;

    @Test
    void getProcessedZip() {
        File processedZip = configTemplateProcessorImpl.getProcessedZip(Map.of(
                "mavenRepositoryURL", "http://maven.com/",
                "pluginVersion", "0.0.1",
                "projectName", "test_proj",
                "systemName", "TEST_SYSTEM",
                "schemasList", "schema1, schema2"
        ));

        System.out.println(processedZip.getAbsolutePath());
    }
}