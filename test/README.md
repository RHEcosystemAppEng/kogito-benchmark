Kogito-gatling-load-testing
=========================
Maven project to do the load testing of Kogito application. Kogito application is launched from the [kogito-examples](https://github.com/kiegroup/kogito-examples). 

## Prerequisites
* Scala version - 2.13 or above.
* Gatling version - 3.5 or above

### Installing Scala & Gatling
Installing [scala 3](https://www.scala-lang.org/download/scala3.html)

Please follow [Gatling Installation instructions](https://gatling.io/docs/gatling/tutorials/installation/)

[Gatling Maven Plugin](https://gatling.io/docs/gatling/reference/current/extensions/maven_plugin/)

If you are planning to use IntelliJ, please install scala plugin.

### Environment

We have `Environment` class to change the root URL of the targeted kogito service. Please modify the base URL as per your scenario.

If you have deployed your Kogito service on open shift then you should be able to get the URL from routes configuration. If you are new to openshift then please go through the documentation to know more about [Open Shift --> Routes](https://docs.openshift.com/dedicated/3/getting_started/access_your_services.html)

Some examples from Environment:

```
val baseURL = scala.util.Properties.envOrElse("baseURL", "http://process-quarkus-example.apps.mw-ocp43.cloud4.lab5.redhat.com")
val baseURL = scala.util.Properties.envOrElse("baseURL", "http://localhost:8080")
```

### Kogito Process Load Testing Example.

We have `KogitoOrderProcessLoadTestSimulation` class to invoke gatling simulation. Make sure to run the Kogito process instance before running this simulation. Please follow instruction on [kogito-examples --> process-springboot-example](https://github.com/kiegroup/kogito-examples/tree/stable/process-springboot-example) project to start the service.

    $mvn gatling:test -Dgatling.simulationClass=com.redhat.kogito.gatling.simulation.KogitoOrderProcessLoadTestSimulation

or simply use below command if you have only one simulation class:

    $mvn gatling:test

### Kogito Process Load Testing Example.

We have `KogitoTravelAgencyLoadTestSimulation` class to invoke gatling simulation. Make sure to run the Kogito process instance before running this simulation. Please follow instruction on [kogito-travel-agency](https://github.com/kiegroup/kogito-examples/tree/stable/kogito-travel-agency) project to start the service.

    $mvn gatling:test -Dgatling.simulationClass=com.redhat.kogito.gatling.simulation.KogitoTravelAgencyLoadTestSimulation

or simply use below command if you have only one simulation class:

    $mvn gatling:test


Sequence of Steps performed as part of Load Testing:

```scala
val postTravel = scenario("Travel Request Scenario ")
    .exec(postTravelRequestHttp)
    .exec(getTravelTasks)
    .exec(postVisaApplicationRequestHttp)
    .pause(15)
    .exec(getTravelConfirmTasks)
    .exec(postConfirmTravelRequestHttp)
```