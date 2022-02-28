package ru.otus.configgenerator.model.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Branch {
    @JsonProperty("name")
    private String name;
    @JsonProperty("commit")
    private Commit commit;
    @JsonProperty("web_url")
    private String webUrl;
}