package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class BenchmarkRunner {
    private Logger logger = LoggerFactory.getLogger(BenchmarkRunner.class);
    @ConfigProperty(name = "process-api/mp-rest/url")
    private String serverUrl;

    @RestClient
    @Inject
    private BenchmarkService benchmarkService;

    public String run(String testType, int noOfTests, int noOfThreads) throws JsonProcessingException,
            InterruptedException {
        TestMetrics metrics = new Worker(testType, noOfTests, noOfThreads).run();
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(metrics);
    }

    private class Worker {
        private String testType;
        private int durationInSeconds;
        private int noOfThreads;
        private AtomicLong itemsCounter = new AtomicLong(0);
        AtomicBoolean timerElapsed = new AtomicBoolean(false);

        private Stats stats;

        private Worker(String testType, int durationInSeconds, int noOfThreads) {
            this.testType = testType;
            this.durationInSeconds = durationInSeconds;
            this.noOfThreads = noOfThreads;
            this.stats = new Stats();
        }

        private TestMetrics run() throws InterruptedException {
            logger.info("Ready to run from {}: {} seconds of type '{}' in {} threads", serverUrl, durationInSeconds,
                    testType,
                    noOfThreads);
            ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
            Timer timer = new Timer("timer");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    logger.info("Timer elapsed");
                    timerElapsed.set(true);
                    timer.cancel();
                }
            }, durationInSeconds * 1000);

            Collection<Callable<Void>> callables =
                    IntStream.rangeClosed(1, noOfThreads).mapToObj(n -> newCallable()).collect(Collectors.toList());
            executor.invokeAll(callables);

            TestMetrics metrics = stats.build();
            logger.info("Completed {} tests in {}ms", metrics.getNoOfExecutions(), metrics.getElapsedTimeMillis());
            return metrics;
        }

        private Callable<Void> newCallable() {
            return () -> {
                while (!timerElapsed.get()) {
                    long index = itemsCounter.incrementAndGet();
                    Execution execution = stats.startOne(index);
                    try {
                        logger.info("Executing: {}", index);
                        executorOfType().execute();
                        execution.stop();
                    } catch (Exception e) {
                        logger.error("Failed to run: {}", e.getMessage());
                        execution.failed();
                    }
                }
                return null;
            };
        }

        private RestExecutor executorOfType() {
            return "hello".equals(testType) ? hello :
                    "notPersisted".equals(testType) ? notPersistedProcess : newOrder;
        }

        private Supplier<OrderItem> newOrderItem = () -> {
            OrderItem orderItem = new OrderItem();
            orderItem.id = itemsCounter.get();
            orderItem.approver = "john - " + orderItem.id;
            orderItem.order = new OrderItem.Order();
            orderItem.order.orderNumber = "12345";
            orderItem.order.shipped = false;
            return orderItem;
        };

        private final RestExecutor hello = () ->
                benchmarkService.hello();
        private final RestExecutor newOrder = () ->
                benchmarkService.newOrderItem(newOrderItem.get());
        private final RestExecutor notPersistedProcess = () ->
                benchmarkService.notPersistedProcess(newOrderItem.get());
    }

    @FunctionalInterface
    interface RestExecutor {
        String execute() throws Exception;
    }
}