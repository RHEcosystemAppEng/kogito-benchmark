# Automation Procedure
Summarized list of steps that can be executed as part of the automated suite to:
* Configure the required setup
* Run one or more configurable test

## OCP Requirements
* Validated on OCP 4.7
* Install the Kogito operator from the OCP console in the `PROJECT_NAME` namespace
* Install the operator `Strimzi 0.22.1 provided by Strimzi` in the `PROJECT_NAME` namespace

## Test Procedure
* [Install the MongoDB operator](../deploy#install-the-mongodb-operator)
* [Deploy the MongoDB instance](../deploy#deploy-the-mongodb-instance)
* [Install MongoDB infra in Kogito operator](../deploy#install-mongodb-infra-in-kogito-operator)
* [Deploy the Kafka instance](../deploy#deploy-the-kafka-instance)
* [Install Kafka infra in Kogito operator](../deploy#install-kafka-infra-in-kogito-operator)
* [Deploying the Kogito App](../deploy#deploying-the-kogito-app)
  * In case multiple tests are executed with different options, it can be deleted
  and restarted before every test execution

## Runtime parameters
| Parameter | Default Value |
|:----|:----|
| Kogito version | 1.8.0.Final |
| Kogito examples app | process-quarkus-example |
| MongoDB operator git | git@github.com:mongodb/mongodb-kubernetes-operator.git |
| MongoDB operator version | v0.2.2 |
| MongoDB db user | developer |
| MongoDB db password | mypass |
| MongoDB db schema | kogito_dataindex |

