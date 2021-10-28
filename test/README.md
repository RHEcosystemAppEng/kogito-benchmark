### Configure tests
Create `test-run/batch.json` containing all the configurations needed to run one or more tests:
- `InfraSetup.appVersion`: benchmark project branch under test
- `InfraSetup.tester`: `name`: LOCAL|VM1|VM2 `hostname`: localhost|<host name>
- `InfraSetup.app`: `name`: LOCAL|VM2 `hostname`: localhost|<host name>`
- `InfraSetup.container`: podman|docker
- `AppInfra`: lists all supported infra structure components (TODO: currently just Mongo and Postgres) - to use, set value to yes, otherwise to no
- `Process`: endpoint configuration under test
- `Warmup.enabled`: if case a warmup run is done before the test runs
- `Warmup.type`: see `Tests.type`
- `Warmup.timeOrCount`: see `Tests.timeOrCount`
- `Warmup.replicas`: see `Tests.replicas`
- `Warmup.users`: see `Tests.users`
- `Tests.type`: requests|duration
- `Tests.timeOrCount`: number of requests|duration of test
- `Tests.runs`: list of tests to be run;
- `Tests.replicas`: number of application replicas when running on OCP
- `Tests.users`: number of concurrent users

Use `test-run/batch-example.json` as template - update only values apart from for the `Tests.runs` part where complete lines can be added/removed 

`InfraSetup`, `AppInfra` definitions are only used by benchmark automation

### Run tests
```
runTests.sh arg1 arg2
```
- `arg1`: the test client to be used - JMETER or QUARKUS
- `arg2`: the index of the test to run or `ALL` to run all configured tests straight 