# Kogito - Debezium Setup on Openshift.
1) Install the strimizi operator on the openshift cluster from operator hub.
2) Install the kafka cluster using strimizi operator. This will setup the kafka broker. Please check the reference documentation for detailed steps. You can use the default configurations without changing them.
3) Download the Debezium mongodb connector and create docker image so that we can deploy using Kafka connect.
   Download the Debezium mongodb connector from (debezium repo)[https://repo1.maven.org/maven2/io/debezium/]. Extract the downloaded archive into the current folder - `debezium-connector-mongodb` and build the docker image using below command.

```shell
FROM quay.io/strimzi/kafka:0.26.0-kafka-3.0.0
USER root:root
COPY ./debezium-connector-mongodb/ /opt/kafka/plugins/debezium-connector-mongodb/
USER 1001
```
Here is the expected folder structure.
```shell
> tree .
├── Dockerfile
├── README.md
├── debezium-connector-mongodb
│ ├── CHANGELOG.md
│ ├── CONTRIBUTE.md
│ ├── COPYRIGHT.txt
│ ├── LICENSE-3rd-PARTIES.txt
│ ├── LICENSE.txt
│ ├── README.md
│ ├── README_JA.md
│ ├── README_ZH.md
│ ├── bson-4.2.1.jar
│ ├── debezium-api-1.7.1.Final.jar
│ ├── debezium-connector-mongodb-1.7.1.Final.jar
│ ├── debezium-core-1.7.1.Final.jar
│ ├── failureaccess-1.0.1.jar
│ ├── guava-30.0-jre.jar
│ ├── mongodb-driver-core-4.2.1.jar
│ └── mongodb-driver-sync-4.2.1.jar
```
```shell
docker build . -t quay.io/lrangine/mongo-debezium-connect:5.0
docker push quay.io/lrangine/mongo-debezium-connect:5.0
```

4) Install the `kafka connect` using the image you have created in the above step. Please find the snippet of the `KafkaConnect` yaml configuration. We have to add image property in the default connector specification.

```yaml
spec:
  bootstrapServers: 'my-kafka-cluster-kafka-bootstrap.debezium-try-out.svc:9092'
  image: 'quay.io/lrangine/mongo-debezium-connect:5.0'
  replicas: 1
  version: 2.8.0
```

5) Monitor the Kafka connect pod logs. Hopefully there are no errors. Debezium mongo connector will expose rest APIs as part of the Kafka pods. Please find (the documentation)[https://docs.confluent.io/platform/current/connect/references/restapi.html] of those APIs.
6) Please modify the below payload and execute below API by opening a terminal on to the Kafka Connect pod. Below API will link the mongo debezium connector to Mongo and Kafka broker.

```shell
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8083/connectors/ -d @- <<-EOF
{
"name": "kogito-connector",
"config": {
"connector.class" : "io.debezium.connector.mongodb.MongoDbConnector",
"tasks.max" : "3",
"consumer.max.poll.records" : "100",
"database.history.consumer.max.poll.records" : "100",
"connect.backoff.max.delay.ms" : "5000",
"mongodb.server.selection.timeout.ms" : "5000",
"mongodb.poll.interval.ms" : "5000",
"mongodb.hosts" : "kogito-sharded-mongo-svc-external.mongodb.svc.cluster.local:27017",
"mongodb.name" : "kogito",
"mongodb.user" : "debezium",
"mongodb.password" : "password",
"mongodb.authsource" : "admin",
"mongodb.members.auto.discover": "true",
"database.include" : "kogito",
"database.history.kafka.bootstrap.servers" : "my-kafka-cluster-kafka-bootstrap.debezium-try-out.svc:9092",
"key.converter": "org.apache.kafka.connect.json.JsonConverter",
"key.converter.schemas.enable": "false",
"value.converter": "org.apache.kafka.connect.json.JsonConverter",
"value.converter.schemas.enable": "false",
"collection.include.list": "kogito.kogitoprocessinstancesevents,kogito.kogitousertaskinstancesevents,kogito.kogitovariablesevents",
"transforms": "unwrap,reroute",
"transforms.unwrap.type": "io.debezium.connector.mongodb.transforms.ExtractNewDocumentState",
"transforms.unwrap.array.encoding": "array",
"transforms.unwrap.drop.tombstones": "false",
"transforms.unwrap.delete.handling.mode": "drop",
"transforms.unwrap.operation.header": "false",
"transforms.reroute.type": "io.debezium.transforms.ByLogicalTableRouter",
"transforms.reroute.topic.regex": "(.*)kogito(.*)events(.*)",
"transforms.reroute.topic.replacement": "kogito-\$2-events",
"transforms.reroute.key.enforce.uniqueness": "false",
"skipped.operations": "u,d",
"tombstones.on.delete": "false"
}
}
EOF
```

7) Check the status of the connector configuration by executing below API. There should not be any errors.
   `curl http://localhost:8083/connectors/{connector-name}/status`
    ```shell
    curl http://localhost:8083/connectors/kogito-connector/status
   ```

8) If you want to delete the kogito-connector setup then you can use below API. `http://localhost:8083/connectors/{connector-name}`
   ```shell
   curl -i -X DELETE -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8083/connectors/kogito-connector
   ```   
9) If you notice any errors related to MongoDB authentication then please make sure the mongo database user you passed in above step is having below permissions.
   **IMPORTANT:** If you are using sharded cluster then make sure to run below commands on the all the mongo database pods (or nodes). Not only mongoS nodes (or pods).

```shell
// Create a role which allows to list the databases
db.runCommand({createRole:"listDatabases",privileges:[{resource:{cluster:true},
actions:["listDatabases"]}],roles:[]})

```
```shell
db.createUser({
user: "debezium",
pwd: "password",
roles: [
{ role: "readWrite", db: "kogito" },
{ role: "read", db: "local" },
{ role: "listDatabases", db: "admin" },
{ role: "read", db: "config" },
{ role: "read", db: "admin" }
]
});

```

10) If there are no errors on the console log and API `curl http://localhost:8083/connectors/{connector-name}/status` status then you should check the Kafka topics if you are receiving any events. You will be receiving events if there are any changes on the kogito database.
```shell
//login to the kafka broker pod.
oc  exec -it kogito-kafka-kafka-0  -- bash
//check if there are any events recieved. 
./kafka-console-consumer.sh --bootstrap-server my-kafka-cluster-kafka-bootstrap.debezium-try-out.svc:9092 --topic kogito-usertaskinstances-events --from-beginning
```

# Application updates
* The application properties must include the property `kogito.persistence.transaction.enabled=true` and all the
other properties starting with prefix `kogito.events.` as in [application-mongo.properties](../test-apps/process-quarkus-example/src/main/resources/application-mongo.properties)
* The application can be deployed using the given [kogito-app.yaml](../test-envs/deploy-OCP/deploy-app/kogito-app.yaml)
* The `Quarkus client` can be deployed using the sample [quarkus-client.yaml](../test-clients/quarkus-client/quarkus-client.yaml)]

# Execution results
* Command executed on the deployed `Quarkus client`:
```shell
curl localhost://benchmark/simple/120/60
```
* Executes 120" of tests against the usual `SimpleHT` process with 60 threads
* The result is documented by the following JSON document:
```json
{
  "noOfExecutions" : 66333,
  "noOfFailures" : 0,
  "minResponseTime" : {
    "index" : 1960,
    "responseTime" : 10
  },
  "maxResponseTime" : {
    "index" : 4,
    "responseTime" : 4022
  },
  "averageResponseTime" : 108,
  "percentile95" : 224,
  "percentile99" : 293,
  "totalTimeMillis" : 7168407,
  "elapsedTimeMillis" : 120087,
  "requestsPerSecond" : 552.0
}
```

# References

* https://strimzi.io/docs/operators/latest/full/deploying.html#kafka-connect-str
* https://strimzi.io/blog/2020/01/27/deploying-debezium-with-kafkaconnector-resource/
* https://strimzi.io/docs/operators/latest/full/deploying.html#creating-new-image-from-base-str
