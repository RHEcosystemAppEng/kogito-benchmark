package com.redhat.fsi.kogito.benchmark;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public class Execution {
    private long index;
    private Consumer<Execution> callback;

    private Instant startTime = Instant.now();
    private Instant endTime;
    private boolean isFailed;

    Execution(long index, Consumer<Execution> callback) {
        this.index = index;
        this.callback = callback;
    }

    public long getIndex() {
        return index;
    }

    public void stop() {
        endTime = Instant.now();
        callback.accept(this);
    }

    public void failed() {
        isFailed = true;
        endTime = Instant.now();
        callback.accept(this);
    }

    public Duration duration() {
        return Duration.between(startTime, endTime);
    }

    public boolean isFailed() {
        return isFailed;
    }
}
