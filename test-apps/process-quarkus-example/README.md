# Process + Quarkus example

## Description

A simple process service for ordering items, as a sequence of a script task (writing out some debug info) and a call activity invoking a sub-process, using a custom `Order` data element.

The sub-process invokes a custom Java service `CalculationService.calculateTotal`, followed by a user task to verify the order.

Based on these two processes (defined using BPMN 2.0 format), the custom data object and custom Java service, a new service is generated that exposes REST operations to create new orders (following the steps as defined in the main and sub-process), or to list and delete active orders.

## Installing and Running

### Prerequisites

You will need:
  - Java 11+ installed
  - Environment variable JAVA_HOME set accordingly
  - Maven 3.6.2+ installed

When using native image compilation, you will also need:
  - [GraalVM 19.1.1](https://github.com/oracle/graal/releases/tag/vm-19.1.1) installed
  - Environment variable GRAALVM_HOME set accordingly
  - Note that GraalVM native image compilation typically requires other packages (glibc-devel, zlib-devel and gcc) to be installed too.  You also need 'native-image' installed in GraalVM (using 'gu install native-image'). Please refer to [GraalVM installation documentation](https://www.graalvm.org/docs/reference-manual/aot-compilation/#prerequisites) for more details.


Run the below command to spin up infinispan and mongo databases.
```shell
 cd ./docker
 docker-compose up 
```

### Compile and Run in Production Mode
We have crated two profiles - infinispan and mongo to run the application using mongo and infinispan as persistence layer.

Below command is used to run the application using infinispan as persistence layer.
```shell
mvn -Dquarkus.profile=infinispan -P infinispan clean package
java -Dquarkus.profile=infinispan -jar target/quarkus-app/quarkus-run.jar
```

Below command is used to run the application using mongo as persistence layer.
```shell
mvn -Dquarkus.profile=mongo -P mongo clean package
java -Dquarkus.profile=mongo -jar target/quarkus-app/quarkus-run.jar
```


### Compile and Run in Local Dev Mode
Below command is used to run the application using infinispan as persistence layer.
```shell
 mvn -Dquarkus.profile=infinispan -P infinispan quarkus:dev
```

Below command is used to run the application using mongo as persistence layer.
```shell
 mvn -Dquarkus.profile=mongo -P mongo quarkus:dev
```

## OpenAPI (Swagger) documentation
[Specification at swagger.io](https://swagger.io/docs/specification/about/)

You can take a look at the [OpenAPI definition](http://localhost:8080/openapi?format=json) - automatically generated and included in this service - to determine all available operations exposed by this service. For easy readability you can visualize the OpenAPI definition file using a UI tool like for example available [Swagger UI](https://editor.swagger.io).

In addition, various clients to interact with this service can be easily generated using this OpenAPI definition.

When running in either Quarkus Development or Native mode, we also leverage the [Quarkus OpenAPI extension](https://quarkus.io/guides/openapi-swaggerui#use-swagger-ui-for-development) that exposes [Swagger UI](http://localhost:8080/swagger-ui/) that you can use to look at available REST endpoints and send test requests.

## Example Usage

Once the service is up and running, you can use the following examples to interact with the service. Note that rather than using the curl commands below, you can also use the [Swagger UI](http://localhost:8080/swagger-ui/) to send requests.

### POST /orders

Allows to create a new order with the given data:

Given data:

```json
{
    "approver" : "john",
    "order" : {
        "orderNumber" : "12345",
        "shipped" : false
    }
}
```

Curl command (using the JSON object above):

```sh
curl -d '{"approver" : "john", "order" : {"orderNumber" : "12345", "shipped" : false}}' -H "Content-Type: application/json" -X POST http://localhost:8080/orders
```
or on windows

```sh
curl -d "{\"approver\" : \"john\", \"order\" : {\"orderNumber\" : \"12345\", \"shipped\" : false}}" -H "Content-Type: application/json" -X POST http://localhost:8080/orders
```

As response the updated order is returned.

Example response:

```json
    {
      "approver": "john",
      "id": "b5225020-4cf4-4e91-8f86-dc840589cc22",
      "order": {
        "orderNumber": "12345",
        "shipped": false,
        "total": 0.529655982561999
      }
    }
```

### GET /orders

Returns list of orders currently active:

```sh
curl -X GET http://localhost:8080/orders
```
Example response:

```json
    [{
      "approver": "john",
      "id": "b5225020-4cf4-4e91-8f86-dc840589cc22",
      "order": {
        "orderNumber": "12345",
        "shipped": false,
        "total": 0.529655982561999
      }
    }]
```

As response an array of orders is returned.

### GET /orders/{id}

Returns order with given id (if active):

```sh
curl -X GET http://localhost:8080/orders/b5225020-4cf4-4e91-8f86-dc840589cc22
```
Example response:

```json
    {
      "approver": "john",
      "id": "b5225020-4cf4-4e91-8f86-dc840589cc22",
      "order": {
        "orderNumber": "12345",
        "shipped": false,
        "total": 0.529655982561999
      }
    }
```

As response a single order is returned if found, otherwise 404 Not Found is returned.

### DELETE /orders/{id}

Cancels order with given id

```sh
curl -X DELETE http://localhost:8080/orders/b5225020-4cf4-4e91-8f86-dc840589cc22
```
Example response:

```json
    {
      "approver": "john",
      "id": "b5225020-4cf4-4e91-8f86-dc840589cc22",
      "order": {
        "orderNumber": "12345",
        "shipped": false,
        "total": 0.529655982561999
      }
    }
```

### GET /orderItems

Getting order items sub processes

```sh
curl -X GET http://localhost:8080/orderItems
```
Example response:

```json
[
  {
    "id":"66c11e3e-c211-4cee-9a07-848b5e861bc5",
    "order":
    {
      "orderNumber":"12345",
      "shipped":false,
      "total":0.537941914075738
    }
  }
]
```

### GET /orderItems/{id}/tasks

Getting user tasks awaiting user action

```sh
curl -X GET http://localhost:8080/orderItems/66c11e3e-c211-4cee-9a07-848b5e861bc5/tasks?user=john
```
Example response:

```json
[
  {"id":"62f1c985-d31c-4ead-9906-2fe8d05937f0","name":"Verify order"}
]
```

### GET /orderItems/{id}/Verify_order/{tid}

Getting user task details

```sh
curl -X GET http://localhost:8080/orderItems/66c11e3e-c211-4cee-9a07-848b5e861bc5/Verify_order/62f1c985-d31c-4ead-9906-2fe8d05937f0?user=john
```
Example response:

```json
{
  "id":"62f1c985-d31c-4ead-9906-2fe8d05937f0",
  "input1":
  {
    "orderNumber":"12345",
    "shipped":false,
    "total":0.537941914075738
  },
  "name":"Verify order"
}
```

### POST /orderItems/{id}/Verify_order/{tid}

Complete user task

```sh
curl -d '{}' -H "Content-Type: application/json" -X POST http://localhost:8080/orderItems/66c11e3e-c211-4cee-9a07-848b5e861bc5/Verify_order/62f1c985-d31c-4ead-9906-2fe8d05937f0?user=john
```


As response the updated order is returned.

Example response:

```json
{
  "id":"66c11e3e-c211-4cee-9a07-848b5e861bc5",
  "order":
  {
    "orderNumber":"12345",
    "shipped":false,
    "total":0.537941914075738
  }
}
```

## Deploying with Kogito Operator

In the [`operator`](operator) directory you'll find the custom resources needed to deploy this example on OpenShift with the [Kogito Operator](https://docs.jboss.org/kogito/release/latest/html_single/#chap_kogito-deploying-on-openshift).
