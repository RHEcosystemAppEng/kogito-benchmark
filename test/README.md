### Configure tests
Create `test-run/batch.json` containing all the configurations needed to run 1+ tests

Use `test-run/batch-example.json` as template - update only values apart from for the `Tests` part where complete lines can be added/removed 

`Tester`, `App`, `Infrasetup` definitions are only relevant when running tests from benchmark automation

### Run tests
```
runTests.sh arg1 arg2
```
- `arg1`: the test client to be used - JMETER or QUARKUS
- `arg2`: the index of the test to run or `ALL` to run all configured tests straight 