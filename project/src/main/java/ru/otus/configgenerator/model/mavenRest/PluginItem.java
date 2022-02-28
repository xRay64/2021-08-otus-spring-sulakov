package ru.otus.configgenerator.model.mavenRest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class PluginItem implements Serializable {
    private String id;
    private String repository;
    private String format;
    private String group;
    private String name;
    private String version;
}
