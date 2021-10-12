# Test procedure

### Before running a test confirm that
- the application and its infrastructure are running (locally, VM2 or OCP lab) 

- application properties `quarkus.vertx.worker-pool-size` and `quarkus.mongodb.max-pool-size` are defined as and if required
  - edit the application.properties file [Properties](../../test-apps/process-quarkus-example/src/main/resources/application-mongo.properties) and then redeploy the application 

- application property `process-api/mp-rest/url` of Quarkus client is set to correct environment
  - edit the application.properties file [Properties](../../test-clients/quarkus-client/src/main/resources/application.properties) 

- application properties of JMeter client are set to correct environment and test values
  - use one of the predefined envXXX.properties files and change properties as needed [Properties](../../test-clients/jmeter-client)

- application and db are reset to initial states
  - restart application pod in OCP (scale deployment down/up)
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