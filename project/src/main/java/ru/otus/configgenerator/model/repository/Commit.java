package ru.otus.configgenerator.model.repository;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.net.URI;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
public class Commit {
    @JsonProperty("id")
    private String id;
    @JsonProperty("committed_date")
    private Date committedDate;
    @JsonProperty("web_url")
    private URI webUrl;
}
