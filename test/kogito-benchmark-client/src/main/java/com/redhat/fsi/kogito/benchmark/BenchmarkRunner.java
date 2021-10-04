package com.redhat.fsi.kogito.benchmark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class BenchmarkRunner {
    private Logger logger = LoggerFactory.getLogger(BenchmarkRunner.class);

    @RestClient
    @Inject
    private BenchmarkService benchmarkService;

    public String run(String testType, int noOfTests, int noOfThreads) throws JsonProcessingException {
        logger.info("Ready to run {} tests of type '{}' in {} threads", noOfTests, testType, noOfThreads);
        itemsCounter = 0;

        Stats stats = new Stats();
        ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
        Collection<Callable<Void>> callables =
                IntStream.rangeClosed(1, noOfTests).mapToObj(n -> newCallable(stats, testType)).collect(Collectors.toList());
        try {
            executor.invokeAll(callables);
        } catch (InterruptedException e) {
            logger.error("RExecution interrupted: {}", e.getMessage());
        }

        TestMetrics metrics = stats.build();
        logger.info("Completed {} tests in {}ms", metrics.noOfExecutions, metrics.totalTimeMillis);

        return new ObjectMapper().writeValueAsString(metrics);
    }

    private Callable<Void> newCallable(Stats stats, String testType) {
        return () -> {
            Execution execution = stats.startOne();
            try {
                logger.info("Executing: {}", stats.noOfRequests);
                executorOfType(testType).execute();
                execution.stop();
            } catch (Exception e) {
                logger.error("Failed to run: {}", e.getMessage());
                execution.failed();
            }
            // TODO
            return null;
        };
    }

    private RestExecutor executorOfType(String testType) {
        return "hello".equals(testType) ? hello : newOrder;
    }

    private final RestExecutor hello = () ->
            benchmarkService.hello();
    private final RestExecutor newOrder = () ->
            benchmarkService.newOrderItem(newOrderItem.get());

    private static long itemsCounter = 0;
    private static Supplier<OrderItem> newOrderItem = () -> {
        OrderItem orderItem = new OrderItem();
        itemsCounter++;
        orderItem.approver = "john - " + itemsCounter;
        orderItem.order = new OrderItem.Order();
        orderItem.order.orderNumber = "12345";
        orderItem.order.shipped = false;
        return orderItem;
    };

    interface RestExecutor {
        String execute() throws Exception;
    }
}