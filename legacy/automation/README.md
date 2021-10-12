# Automation Procedure
Summarized list of steps that can be executed as part of the automated suite to:
* Configure the required setup
* Run one or more configurable test

## OCP Requirements
* Validated on OCP 4.7
* Install the Kogito operator from the OCP console in the `PROJECT_NAME` namespace
* Install the operator `Strimzi 0.22.1 provided by Strimzi` in the `PROJECT_NAME` namespace

## Test Procedure
* [Install the MongoDB operator](../../test-envs/deploy-OCP#install-the-mongodb-operator)
* [Deploy the MongoDB instance](../../test-envs/deploy-OCP#deploy-the-mongodb-instance)
* [Install MongoDB infra in Kogito operator](../../test-envs/deploy-OCP#install-mongodb-infra-in-kogito-operator)
* [Deploy the Kafka instance](../../test-envs/deploy-OCP#deploy-the-kafka-instance)
* [Install Kafka infra in Kogito operator](../../test-envs/deploy-OCP#install-kafka-infra-in-kogito-operator)
* [Deploying the Kogito App](../../test-envs/deploy-OCP#deploying-the-kogito-app)
  * In case multiple tests are executed with different options, it can be deleted
  and restarted before every test execution
* Build the Gatling application (build-config)
* Build the Persist Gatling report to MongoDB
 * Image is uploaded in personal Quay.io 
* Run the Gatling test
* Fetch results from Nooba/MongoDB and update result page in Git repo
* Fetch system metrics from OCP platform manually for the Kogito App pod and update result page in Git repo

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

## Test run parameters
| Parameter | Default Value | Possible Values | Description |
|:----|:----|:----|:----|
| baseURL - string | http://localhost:8080 |  |the url where the tested application is running |
| testCase - string - all testCase options require numberOfUsers, duration to be set | CONST_USERS_PER_SEC | CONST_USERS_PER_SEC | Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at regular intervals |
| |  | RAMP_USERS | Injects a given number of users distributed evenly on a time window of a given duration. |
|  |  | CONST_CONCURRENT_USERS | Injects so that number of concurrent users in the system is constant |
| maxDuration - number in minutes | 1 | no limit | The time after which the simulation is stopped |
| maxResponseTime - number in milliseconds | 50000 | no limit | The maximum response time allowed - evaluated after simulation, if greater, simulation deemed failed | 
| numberOfUsers - number | 1 | no limit | depending on scenario if concurrent or total number of users |
| duration - number in minutes | 1 | no limit | how long a scenario is to be run |
