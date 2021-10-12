package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestData {
    public Data data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        public long testIndex;
        public String name;

        @java.lang.Override
        public java.lang.String toString() {
            return "{" +
                    "testIndex=" + testIndex +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TestData{" +
                "data=" + data +
                '}';
    }
}
