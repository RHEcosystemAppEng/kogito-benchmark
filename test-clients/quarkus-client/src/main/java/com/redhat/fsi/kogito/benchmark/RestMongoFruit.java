package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestMongoFruit {
    @JsonIgnore
    public long id;

    public String name;
    public String description;

    @Override
    public String toString() {
        return "RestMongoFruit{" +
                "name='" + name + '\'' +
                ", description=" + description +
                '}';
    }
}
