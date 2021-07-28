# Table of Contents
* [Kogito Benchmark](#kogito-benchmark)
  * [System Architecture](#system-architecture)
  * [Business Process Model](#business-process-model)  
  * [Test strategy](#test-strategy)
  * [Metrics specifications](#metrics-specifications)
  * [Procedures](#procedures)
  * [References](#references)

# Kogito Benchmark

Purpose of this project is benchmarking [Kogito](https://kogito.kie.org/) application for a defined business process model 
using MongoDB as persistence store.
The objective is to define a repeatable procedure to generate structured data for the metrics defined in [Metrics specifications](#metrics-specifications),
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
Strategy 1: 
Running a fixed amount of concurrent users for a fixed amount of time using `constantConcurrentUsers`
establishing concurrent user limit with reasonable execution time

Strategy 2:

**TODO** Move to some troubleshooting section in the test procedure doc
Every time a new `Order` is defined, the related Pod in the OCP platform will log a message like:
```text
Order has been created Order[12345] with assigned approver JOHN
```

## Metrics specifications

###Test Cases

| Test Case | Details |
|----------|------|
|  | Strategy 1 - prelim tests |
| ccu100 | constantConcurrentUsers: 100; duration 5 minutes |
| ccu500 | constantConcurrentUsers: 500; duration 5 minutes |
| ccu1000 | constantConcurrentUsers: 1000; duration 5 minutes |

###Results
| Target   |    ccu100    | ccu500 | ccu1000 |
|----------|------|----|---|
| Latency 95% PCT (ms)  |  1129 | 
| Latency 99% PCT (ms)  |  1231 |
| Av. Response (ms) | 1214 | 
| Peak Response (ms) | 1232 |
| Error Rate (%) | 0 | 
| Throughput (transactions / s - TPS) | 10 |
| Runtime memory (MB / pod) | |
| Runtime startup (ms) | |
| CPU Usage (% / pod) | |

###Gatling Report Data
![Gatling Report](./GatlingReportData.png)

![Gatling Report2](./GatlingReportData2.png)

## Procedures
* [Configuration](./deploy/README.md)
* [Test procedure](./test/README.md) **WIP**

## References
* [Project Requirements](https://docs.google.com/document/d/1AtAfTiFSB2VcI84zg-ocPTnYy_1HCK556FiWt_iPkiM/edit?usp=sharing)
* [Asana Board](https://app.asana.com/0/1200541157872337/board)
* [GitHub repository](https://github.com/RHEcosystemAppEng/kogito-benchmark)
