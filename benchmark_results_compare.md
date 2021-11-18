# Benchmark Comparisons

## 1.11.Final vs 2.0.0.SNAPSHOT

Comparison of test runs done **on premise** 
on version **1.11.0-FINAL** and **version 2.0.0.-SNAPSHOT** (containing memory leak and mongo index fix)
for **MongoDB** and **PostgresQL** running in docker containers

### Remarks and Observations

- Before each run the Kogito app was restarted and the persistence container created anew 
- Mongo properties: quarkus.vertx.worker-pool-size=100,quarkus.mongodb.max-pool-size=100
- Postgres properties: quarkus.vertx.worker-pool-size=100 

- As expected, throughput does grow significantly for the Mongo test runs on the indexed fixed version 
from 150RPS to around 1400RPS
- On the other hand, throughput values for postgres runs decrease by about 25% from around 1200 (1.11.0-FINAL) to 900 (memory, index fix) 

[Results - graphic](https://htmlpreview.github.io/?https://github.com/RHEcosystemAppEng/kogito-benchmark/blob/CoreFix-mem-idx-2.0.0-snap/test-results/benchmarkReportMultipleBatches.html)

[Results - data](./test-results/test-run-mongo-postgres-1.11.0-2.0.0.zip)

## 2.0.0.SNAPSHOT - different worker and mongoDb pool sizes

Comparison of test runs done **on premise**
on version **version 2.0.0.-SNAPSHOT** (containing memory leak and mongo index fix)
for **MongoDB** running in docker containers with different worker and mongoDb pool settings

[Results - graphic](https://htmlpreview.github.io/?https://github.com/RHEcosystemAppEng/kogito-benchmark/blob/CoreFix-mem-idx-2.0.0-snap/test-results/benchmarkReportMultipleBatches2.html)

[Results - data](./test-results/test-run-10-100-pools.zip)

## 2.0.0.SNAPSHOT - postgres reactive add-on with pool size

```
quarkus.vertx.worker-pool-size=100
quarkus.datasource.reactive.max-size=100
```

[Results - graphic](https://htmlpreview.github.io/?https://github.com/RHEcosystemAppEng/kogito-benchmark/blob/CoreFix-mem-idx-2.0.0-snap/test-results/benchmarkReportPostgresReactive100.html)

[Results - data](./test-results/test-run-postgres-2.0.0-snap-reactive-100.zip)

## 2.0.0.SNAPSHOT - postgres jdbc add-on with pool size

```
quarkus.vertx.worker-pool-size=100
quarkus.datasource.jdbc.max-size=100
```

[Results - graphic](https://htmlpreview.github.io/?https://github.com/RHEcosystemAppEng/kogito-benchmark/blob/CoreFix-mem-idx-2.0.0-snap/test-results/benchmarkReportPostgresJdbc100.html)

[Results - data](./test-results/test-run-postgres-2.0.0-snap-jdbc-100.zip)