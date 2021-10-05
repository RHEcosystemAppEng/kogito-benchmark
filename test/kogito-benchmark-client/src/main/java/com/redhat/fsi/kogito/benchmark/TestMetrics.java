package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder
@Data
public class TestMetrics {
    private long noOfExecutions;
    private long noOfFailures;
    private long minResponseTime;
    private long maxResponseTime;
    private long averageResponseTime;
    private long totalTimeMillis;
    private long elapsedTimeMillis;

    private double requestsPerSecond;
}
