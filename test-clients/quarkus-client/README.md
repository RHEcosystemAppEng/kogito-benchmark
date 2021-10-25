# Java Quarkus Benchmark Client

## Requirements
* Java 11
* Maven 3.x
* Internet access to download needed artifacts

## Design notes
* Using [REST Easy](https://resteasy.github.io/) to define [BenchmarkService](./src/main/java/com/redhat/fsi/kogito/benchmark/BenchmarkService.java)
  interface that abstract the REST aspects as a regular Java interface  
  * Configuration is defined in [application.properties](./src/main/resources/application.properties)

## Launching the application
```shell
mvn quarkus:dev
```

### Configuring a different application deployment
Edit [application.properties](./src/main/resources/application.properties) and change the value of:
```properties
process-api/mp-rest/url=http://localhost:8080
```
Given the hot deployment capabilities of Quarkus, there is no need to restart the application.

## Running the test
The client application exposes an API that can be used to start the test:
```properties
http://localhost:9090/benchmark/TYPE/DURATION/THREADS
```
Where:
* TYPE can be any of: 
  * `hello`: triggers the `greeting` endpoint of the remote Kogito application
  * `notPersisted`: triggers the `notPersistedProcess` in the remote Kogito application
  * `orders` (default): triggers the `orders` in the remote Kogito application
  * `simple`: triggers the `simpleHT` in the remote Kogito application
* DURATION is the duration in seconds of the test
* THREADS is the number of parallel threads to spawn

Examples:
```shell
curl -X GET http://localhost:9090/benchmark/notPersisted/300/1
```
Result is in JSON format:
```json
{
  "noOfExecutions" : 112425,
  "noOfFailures" : 0,
  "minResponseTime" : {
    "index" : 1439,
    "responseTime" : 1
  },
  "maxResponseTime" : {
    "index" : 39019,
    "responseTime" : 566
  },
  "averageResponseTime" : 2,
  "percentile95" : 4,
  "percentile99" : 8,
  "totalTimeMillis" : 256618,
  "elapsedTimeMillis" : 300005,
  "requestsPerSecond" : 374.0
}
```
**Note** The `index` attribute in `minResponseTime` and `maxResponseTime` respresent the (first) index of the request 
for which that time what calculated
