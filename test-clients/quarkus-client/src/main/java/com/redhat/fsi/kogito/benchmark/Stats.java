package com.redhat.fsi.kogito.benchmark;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Stats {
    private Instant startTime = Instant.now();

    private long noOfRequests;
    private long noOfFailures;
    private TestMetrics.ResponseTimeAndID minResponseTime = TestMetrics.ResponseTimeAndID.defaultMinResponseTime();
    private TestMetrics.ResponseTimeAndID maxResponseTime = TestMetrics.ResponseTimeAndID.defaultMaxResponseTime();
    private long totalTimeMillis;
    private List<Long> responseTimes=new ArrayList<>(2000);

    public Execution startOne(long index) {
        noOfRequests++;
        return new Execution(index, callback);
    }

    private Consumer<Execution> callback = execution -> completed(execution);

    private void completed(Execution execution) {
        long responseTime = execution.duration().toMillis();
        responseTimes.add(responseTime);
        totalTimeMillis += responseTime;
        minResponseTime.minOf(execution);
        maxResponseTime.maxOf(execution);
        noOfFailures += execution.isFailed() ? 1 : 0;
    }

    public TestMetrics build() {
        Instant endTime = Instant.now();
        Duration testDuration = Duration.between(startTime, endTime);

        TestMetrics metrics = new TestMetrics();
        metrics.setNoOfExecutions(noOfRequests);
        metrics.setNoOfFailures(noOfFailures);
        metrics.setTotalTimeMillis(totalTimeMillis);
        metrics.setElapsedTimeMillis(testDuration.toMillis());
        metrics.setMinResponseTime(minResponseTime);
        metrics.setMaxResponseTime(maxResponseTime);
        metrics.setAverageResponseTime(totalTimeMillis / noOfRequests);
        metrics.setRequestsPerSecond(1000 * noOfRequests / testDuration.toMillis());

        Collections.sort(responseTimes);
        int index95 = (int)(responseTimes.size() * .95);
        metrics.setPercentile95(responseTimes.get(index95));
        int index99 = (int)(responseTimes.size() * .99);
        metrics.setPercentile99(responseTimes.get(index99));

        return metrics;
    }
}
