package com.redhat.kogito.benchmark;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static final String GATLING_FILE_PATH_ENV = "GATLING_FILE_PATH_ENV";
    public static final String GATLING_FILE_PATH = envVariableExtractor(GATLING_FILE_PATH_ENV);
    public static final String LAST_SIMULATION_ID = getLastSimulationId();
    public static final String MONGODB_CONNECTION_URI = "MONGODB_CONNECTION_URI";
    public static final String MONGODB_NAME = "MONGODB_NAME";
    public static final String MONGODB_COLLECTION_NAME = "MONGODB_COLLECTION_NAME";
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * com.redhat.kogito.benchmark.Main entry point of the application. It will execute the following steps:
     * - Read gatling benchmark test results report json file
     * - Connect to the specified mongo db database and insert the json report
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            String readString = Files.readString(Paths.get(getStatisticFilePath()), StandardCharsets.UTF_8);
            LOGGER.info("path to the json file: " + getStatisticFilePath());

            var mongoConnectionString = envVariableExtractor(MONGODB_CONNECTION_URI);

            try (MongoClient mongoClient = MongoClients.create(mongoConnectionString)) {
                var db = mongoClient.getDatabase(envVariableExtractor(MONGODB_NAME));
                var collection = db.getCollection(envVariableExtractor(MONGODB_COLLECTION_NAME));

                final Document gatlingResult = new Document(LAST_SIMULATION_ID, Document.parse(readString));
                collection.insertOne(gatlingResult);

                LOGGER.info("gatling results saved successfully in " +
                        envVariableExtractor(MONGODB_CONNECTION_URI) + "/" + envVariableExtractor(MONGODB_NAME)
                        + " database.");
            }

        } catch (Exception e) {
            LOGGER.error("com.redhat.kogito.benchmark.Main method failed due to: ", e);
        }
    }


    private static String getStatisticFilePath() {
        return GATLING_FILE_PATH + File.separator + LAST_SIMULATION_ID + "/js/global_stats.json";
    }

    private static String getLastSimulationId() {
        String lastRunFilePath = GATLING_FILE_PATH + File.separator + "lastRun.txt";
        try (Stream<String> fileContentLines = Files.lines(Paths.get(lastRunFilePath))) {
            return fileContentLines.findFirst()
                    .orElseThrow(() -> new RuntimeException("lastRun.txt file is empty!"));
        } catch (IOException e) {
            LOGGER.error("Problem occurred while reading lastRun.txt file: ", e);
        }
        throw new RuntimeException("lastRun.txt cannot be accessed!");
    }

    private static String envVariableExtractor(final String envName) {
        String envValue = System.getenv(envName);
        if (envValue == null) {
            throw new RuntimeException(envName + " env is not available.");
        } else if (envValue.isEmpty()) {
            throw new RuntimeException(envName + " env value is empty.");
        } else if (envValue.endsWith(File.separator)) {
            envValue = envValue.substring(0, envValue.lastIndexOf(File.separator));
        }
        return envValue;
    }
}


