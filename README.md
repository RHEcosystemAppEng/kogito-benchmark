# Table of Contents
* [Kogito Benchmark](#kogito-benchmark)
  * [System Architecture](#system-architecture)
  * [Business Process Model](#business-process-model)  
  * [Test strategy](#test-strategy)
  * [Metrics specifications and results](#metrics-specifications-and-results)
  * [Procedures](#procedures)
  * [References](#references)
  * [Troubleshooting](#troubleshooting)

# Kogito Benchmark

Purpose of this project is benchmarking [Kogito](https://kogito.kie.org/) application for a defined business process model 
using MongoDB as persistence store.
The objective is to define a repeatable procedure to generate structured data for the metrics defined in [Metrics specifications and results](#metrics-specifications-and-results),
to easily monitor system performance as the product evolves.  

The following table shows the configuration of the test environment, 
| Target   |      Specs      |
|----------|:-------------:|
| Kogito version |  1.8.0.Final |
| Runtime environment |  [OpenShift](https://console-openshift-console.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com) |
| JVM runtime | Quarkus |
| Data persistence | MongoDB | 
| Testing framework | [Gatling](https://gatling.io/) |

## System Architecture
The following diagram illustrates the basic architecture of the testing scenario:

![Test Architecture](./BenchmarkArchitecture.png)

**Note**: `Data-Index` infrastructure is not part of this initial setup. Also, the initial metrics will not 
validate that the Kafka broker actually sends the expected events.

## Business Process Model
The Business Process Model under test comes from the [Process + Quarkus example](https://github.com/kiegroup/kogito-examples/tree/stable/process-quarkus-example)
application, that is a simple process service for ordering items.
In particular, we  will create new instances of the `Order` data element using a POST REST request, defined by the [orders](https://github.com/kiegroup/kogito-examples/blob/stable/process-quarkus-example/src/main/resources/org/kie/kogito/examples/orders.bpmn2)
process:
![Orders process](./OrdersProcess.png)

## Test strategy
[Daniele]: can we add more details? Maybe an example of how the requests are distributed along the time

Strategy 1: constant number of users at all times 
Gatling:  `constantConcurrentUsers`

Strategy 2: number of users achieved during given time
Gatling: `rampUsers`

Strategy 3: constant number of requests at all times
Gatling: `constantUsersPerSec`

## Metrics specifications

**Strategy 1**

| test run | pods | users | time (min) |
|----------|----|----|----|
| 1 | 1 | 100 | 5 |
| 2 | 2 | 100 | 5 |
| 3 | 3 | 100 | 5 |
| 4 | 1 | 100 | 15 |
| 5 | 3 | 100 | 15 |

**Strategy 2**

| test run | pods | users | time (min) |
|----------|----|----|----|
| 1 | 1 | 50000 | 10 |

**Strategy 3**

| test run | pods | users | time (min) |
|----------|----|----|----|
| 1 | 1 | 700 | 5 | 
| 2 | 3 | 700 | 5 |origin 

## Results

**Strategy 1**

| Run | Latency 95% PCT (ms) | Latency 99% PCT (ms) | Av. Response (ms) | Peak Response (ms) | Error Rate (%)  | Throughput (transactions / s - TPS) | Runtime memory (MiB / pod) | CPU Usage (m / pod) | Runtime startup (ms) |   
|----|----|----|----|----|----|----|----|----|----|
| 1 | 1253 | 1948 | 617 | 6782 | 0 | 160 | 1107 | 768 | |  
| 2 | 644 | 1414 | 518 | 3557 | 0 | 191 | 1287 (639,648) | 598 (275,323) | |  
| 3 | 1004 | 1648 | 557 | 4122 | 0 | 178 | 1385 (501,512,372) | 928 (268,258,402) | |
| 4 | 1438 | 2021 | 636 | 7770 | 0 | 156 | 3096 | 423 | |  
| 5 | 734 | 1499 | 534 | 5406 | 0 | 186 | 3668 (1416,1038,1432) | 973 (271,437,265) | |

**Strategy 2**

| Run | Latency 95% PCT (ms) | Latency 99% PCT (ms) | Av. Response (ms) | Peak Response (ms) | Error Rate (%)  | Throughput (transactions / s - TPS) | Runtime memory (MiB / pod) | CPU Usage (m / pod) | Runtime startup (ms) |   
|----|----|----|----|----|----|----|----|----|----|
| 1 | 986 | 5690 | 608 | 8454 | 0 | 83 | 1216 | 586 | |  

**Strategy 3**

| Run | Latency 95% PCT (ms) | Latency 99% PCT (ms) | Av. Response (ms) | Peak Response (ms) | Error Rate (%)  | Throughput (transactions / s - TPS) | Runtime memory (MiB / pod) | CPU Usage (m / pod) | Runtime startup (ms) |   
|----|----|----|----|----|----|----|----|----|----|
| 1 | 25000 | 36900 | 12000 | 84000 | 46 | 648 | - | - | |  
| 2 | 61000 | 63000 | 20000 | 68000 | 92 | 600 | - | - | | 

* [Example Run](./results/exRun.png)


## Procedures
* [Configuration](./deploy/README.md)
* [Test procedure](./test/README.md) **WIP**

## References
* [Project Requirements](https://docs.google.com/document/d/1AtAfTiFSB2VcI84zg-ocPTnYy_1HCK556FiWt_iPkiM/edit?usp=sharing)
* [Asana Board](https://app.asana.com/0/1200541157872337/board)
* [GitHub repository](https://github.com/RHEcosystemAppEng/kogito-benchmark)

## Troubleshooting
* Fetch Orders from REST API: the URL is `ROUTE_OF_APPLICATION/orders`
* Access `Swagger UI`: the URL is `ROUTE_OF_APPLICATION/swagger-ui`
* Every time a new `Order` is defined, the related Pod in the OCP platform will log a message like:
```text
Order has been created Order[12345] with assigned approver JOHN
```

