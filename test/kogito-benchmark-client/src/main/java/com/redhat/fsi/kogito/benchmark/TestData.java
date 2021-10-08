package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestData {
    public long testIndex;
    public String name;

    @java.lang.Override
    public java.lang.String toString() {
        return "TestData{" +
                "testIndex=" + testIndex +
                ", name='" + name + '\'' +
                '}';
    }
}
