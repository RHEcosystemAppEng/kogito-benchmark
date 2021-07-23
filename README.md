# Kogito Benchmark

Purpose of this project is to

| Target   |      Specs      |
|----------|:-------------:|
| Kogito version |  1.8.0.Final |
| Runtime environment |  [OpenShift](console-openshift-console.apps.mw-ocp4.cloud.lab.eng.bos.redhat.com) |

## System Architecture
The following diagram illustrates the basic architecture of the testing scenario:

![Test Architecture](./BenchmarkArchitecture.png)

**Note**: `Data-Index` infrastructure is not part of this initial setup. Also, the initial metrics will not 
validate that the Kafka broker actually sends the expected events.

## Test strategy

## Metrics specifications

## Procedures
* [Configuration](./deploy/README.md)
* [Test procedure](./test/README.md) **WIP**

## References
* [Project Requirements](https://docs.google.com/document/d/1AtAfTiFSB2VcI84zg-ocPTnYy_1HCK556FiWt_iPkiM/edit?usp=sharing)
* [Asana Board](https://app.asana.com/0/1200541157872337/board)
* [GitHub repository](https://github.com/RHEcosystemAppEng/kogito-benchmark)
