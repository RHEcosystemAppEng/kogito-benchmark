# Test procedure

### Before running a test confirm that
- the Kogito application and its infrastructure are running (locally, VM2 or OCP lab) 

- Kogito application properties `quarkus.vertx.worker-pool-size` and `quarkus.mongodb.max-pool-size` are defined as and if required
  - edit the application.properties file [Properties](../../test-apps/process-quarkus-example/src/main/resources/application-mongo.properties) and then redeploy the application 

- Quarkus client application properties `quarkus.vertx.worker-pool-size` (same value like in Kogito application properties) and `process-api/mp-rest/url` are defined as required
  - edit the application.properties file [Properties](../../test-clients/quarkus-client/src/main/resources/application.properties) 

- JMeter client application properties are set to correct environment and test values
  - use one of the predefined envXXX.properties files and change properties as needed [Properties](../../test-clients/jmeter-client)

- Kogito application and persistence are reset to initial states
  - restart application pod in OCP with required replicas 
    - chose project `fsi-kogito-benchmarking` -> `Installed Operators` -> `kogito-operator.v1.11.1` -> `KogitoRuntime details`, open
      process-quarkus-example YAML, update spec: replicas: x, save
  - run script [MongoDB Reset](../mongodb/cleanup-OCP.sh) - see  [MongoDB README](../mongodb/README.md) for more details

### Run Test

#### Use Quarkus Client

Run client from 1st shell
```shell
ssh <username>@appeng-vm01.cloud.lab.eng.bos.redhat.com
cd /opt/fsi-kogito/kogito-benchmark/test-clients/quarkus-client
mvn quarkus:dev
```
Run test request from 2nd shell

curl http://localhost:9090/benchmark/TYPE/DURATION/THREADS

```shell
ssh <username>@appeng-vm01.cloud.lab.eng.bos.redhat.com
curl http://localhost:9090/benchmark/simple/120/1
```
Read test results from shell output

#### Use JMeter Client
Run client from shell - use the properties file name you edited earlier as `arg` for the `runTest` script 
```shell
ssh <username>@appeng-vm01.cloud.lab.eng.bos.redhat.com
cd /opt/fsi-kogito/kogito-benchmark/test-clients/jmeter-client
runTest.sh envOCPlab
```
Read test results from defined output path `results_location`/`results_file`.csv