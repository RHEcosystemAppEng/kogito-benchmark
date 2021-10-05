package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder
@Data
public class TestMetrics {
    @Data
    static class ResponseTimeAndID {
        private long index;
        private long responseTime;

        public static ResponseTimeAndID defaultMinResponseTime() {
            ResponseTimeAndID result = new ResponseTimeAndID();
            result.index = 0;
            result.responseTime = Long.MAX_VALUE;
            return result;
        }

        public static ResponseTimeAndID defaultMaxResponseTime() {
            ResponseTimeAndID result = new ResponseTimeAndID();
            result.index = 0;
            result.responseTime = 0;
            return result;
        }

        void minOf(Execution execution){
            long responseTime = execution.duration().toMillis();
            if (responseTime<this.responseTime){
                this.responseTime=responseTime;
                this.index=execution.getIndex();
            }
        }
        void maxOf(Execution execution){
            long responseTime = execution.duration().toMillis();
            if (responseTime>this.responseTime){
                this.responseTime=responseTime;
                this.index=execution.getIndex();
            }
        }
    }

    private long noOfExecutions;
    private long noOfFailures;
    private ResponseTimeAndID minResponseTime;
    private ResponseTimeAndID maxResponseTime;
    private long averageResponseTime;
    private long percentile95;
    private long percentile99;
    private long totalTimeMillis;
    private long elapsedTimeMillis;

    private double requestsPerSecond;
}
