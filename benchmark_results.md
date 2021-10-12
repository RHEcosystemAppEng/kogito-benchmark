# Benchmark Results

Following numbers were gathered targeting the [forked version of process-quarkus-example](https://github.com/RHEcosystemAppEng/kogito-benchmark/tree/main/test/process-quarkus-example) on different compute environments.
The reason we use a forked version is we have added 2 additional business process definitions 
_([emptyProcess, notPersistedProcess](https://github.com/RHEcosystemAppEng/kogito-benchmark/tree/main/test/process-quarkus-example/src/main/resources/org/kie/kogito/examples), [SimpleHT](https://github.com/RHEcosystemAppEng/kogito-benchmark/tree/main/test/process-quarkus-example/src/main/resources/com/redhat/fsi))_ for our testing purposes.

Mainly 2 options were tested:
1. Kogito application deployed in a Virtual machine
2. Kogito application deployed in a Red Hat Openshift cluster.


#### VM Details: 🖥️
- Intel Core i7 9xx (Nehalem Class Core i7) @2.4GHz z (4 cores / 4 threads)
- 16GB Memory
- Red Hat Enterprise Linux Server release 7.9 (Maipo)

#### Config Details: ⚒️

- Java version: openjdk 11.0.12 2021-07-20 LTS
- Persistence: Mongo DB v4.4.9
- Kogito version: 1.11.0.Final
- Concurrence Strategy: CONST_CONCURRENT_USERS

## VM deployment

|   🧰 Test Engine	|   Business Process Name ➙ Config Name	| Persistence ➙ Config Name | Replicas 	|  👥 Users 	|  ⏰ Duration 	|  🔀 Request count 	|  ⚠️ Ok/KO count 	| ⚡ mean response time | 🛎️ mean requests/sec 	|
|---	|---	|:-----:	|:---:|    ---:|  :---: | ---:| :---:| ---:| ---:|
|  Gatling	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  1 	|   5 min	|  30688 	|  28232/2456 	|  2 ms 	|   503.08/sec	|
|  Gatling 	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  4 	|   5 min	|  54197 	|  28232/25965 	|  4 ms 	|   903.28/sec	|
|  Gatling 	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  8 	|   5 min	|  60516 	|  30875/29641 	|  5 ms 	|   992.06/sec	|
|  Gatling 	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  20 	|   5 min	|  65462 	|  33255/32207 	|  10 ms 	|   1073.14/sec	|
|  Gatling 	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  40 	|   5 min	|  318394 	|  148050/170344 	|  19 ms 	|  1057.78/sec 	|
|  Gatling 	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  60 	|   5 min	|  334250 	|  147883/186367 	|  28 ms 	|  1110.46/sec 	|
|  Gatling 	|  notPersistedProcess ➙ default |  ❌ No persistence	|  1 	|  1 	|   5 min	|  150819 	|  131159/19660 	|  2 ms 	|  501.06/sec 	|
|  Gatling 	|  notPersistedProcess ➙ default |  ❌ No persistence	|  1 	|  4 	|   5 min	|  173586 	|  144145/29441 	|  2 ms 	|  576.69/sec 	|
|  Gatling 	|  notPersistedProcess ➙ default |  ❌ No persistence	|  1 	|  8 	|   5 min	|  178186 	|  146601/31585 	|  3 ms 	|  591.98/sec 	|
|  Gatling 	|  notPersistedProcess ➙ default |  ❌ No persistence	|  1 	|  20 	|   5 min	|  181766 	|  141168/40598 	|  12 ms 	|  603.87/sec 	|
|  Gatling 	|  notPersistedProcess ➙ default |  ❌ No persistence	|  1 	|  40 	|   5 min	|  187024 	|  153349/33675 	|  27 ms 	|  621.34/sec 	|
|  Gatling 	|  notPersistedProcess ➙ default |  ❌ No persistence	|  1 	|  60 	|   5 min	|  194124 	|  150145/43979 	|  43 ms 	|  644.93/sec 	|
|  Java-Quarkus 	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  1 	|   2 min	|  10777 	|  10777/0 	|  10 ms 	|  89/sec 	|
|  Java-Quarkus 	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  4 	|   2 min	|  22855 	|  22855/0 	|  20 ms 	|  190/sec 	|
|  Java-Quarkus 	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  8 	|   2 min	|  26289 	|  26289/0 	|  35 ms 	|  219/sec 	|
|  Java-Quarkus 	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  20 	|   2 min	|  27221 	|  27221/0 	|  87 ms 	|  226/sec 	|
|  Java-Quarkus 	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  40 	|   2 min	|  26563 	|  26563/0 	|  180 ms 	|  221/sec 	|
|  Java-Quarkus 	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  60 	|   2 min	|  26291 	|  26291/0 	|  273 ms 	|  218/sec 	|
|  Java-Quarkus 	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  1 	|   2 min	|  33402 	|  33402/0 	|  3 ms 	|  278/sec 	|
|  Java-Quarkus 	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  4 	|   2 min	|  91318 	|  91318/0 	|  4 ms 	|  760/sec 	|
|  Java-Quarkus 	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  8 	|   2 min	|  113305 	|  113305/0 	|  7 ms 	|  944/sec 	|
|  Java-Quarkus 	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  20 	|   2 min	|  126711 	|  126711/0 	|  18 ms 	|  1055/sec 	|
|  Java-Quarkus 	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  40 	|   2 min	|  130201 	|  130201/0 	|  36 ms 	|  1084/sec 	|
|  Java-Quarkus 	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  60 	|   2 min	|  127817 	|  127817/0 	|  55 ms 	|  1063/sec 	|


## Red Hat Openshift deployment


|   🧰 Test Engine	|   Business Process Name ➙ Config Name	| Persistence ➙ Config Name | Replicas 	|  👥 Users 	|  ⏰ Duration 	|  🔀 Request count 	|  ⚠️ Ok/KO count 	| ⚡ mean response time | 🛎️ mean requests/sec 	|
|---	|---	|:-----:	|:---:|    ---:|  :---: | ---:| :---:| ---:| ---:|
|   Gatling	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  1 	|   1 min	|  13270 	|  13270/0 	|  4 ms 	|   221/sec	|
|   Gatling	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  4 	|   1 min	|  39136 	|  28244/10892 	|  5 ms 	|   641/sec	|
|   Gatling	|  emptyProcess ➙ default |  ❌ No persistence	|  1 	|  8 	|   1 min	|  50958 	|  28232/22726 	|  5 ms 	|   849/sec	|
|   Gatling	|  notPersistedProcess ➙ default |  ❌ No persistence	|  1 	|  4 	|   5 min	|  439752 	|  439752/0 	|  2 ms 	|   1465/sec	|
|   Gatling	|  notPersistedProcess ➙ default |  ❌ No persistence	|  2 	|  4 	|   5 min	|  442718 	|  442718/0 	|  2 ms 	|   1475/sec	|
|   Gatling	|  notPersistedProcess ➙ default |  ❌ No persistence	|  5 	|  4 	|   5 min	|  381256 	|  381256/0 	|  2 ms 	|   1270/sec	|
|   Gatling	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  1 	|   5 min	|  8844 	|  8844/0 	|  33 ms 	|   29.38/sec	|
|   Gatling	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  4 	|   5 min	|  19350 	|  19350/0 	|  62 ms 	|   64.28/sec	|
|   Gatling	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  8 	|   5 min	|  22528 	|  22528/0 	|  106 ms 	|   74.84/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  1 	|   5 min	|  10111 	|  10111/0 	|  29 ms 	|   33.7/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  2 	|  1 	|   5 min	|  10549 	|  10549/0 	|  28 ms 	|   35.2/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  1 	|   5 min	|  9184 	|  9184/0 	|  31 ms 	|   31/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  4 	|   5 min	|  20024 	|  20024/0 	|  59 ms 	|   66.8/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  2 	|  4 	|   5 min	|  20397 	|  20397/0 	|  58 ms 	|   68/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  4 	|   5 min	|  20291 	|  20291/0 	|  59 ms 	|   68/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  8 	|   5 min	|  23387 	|  23387/0 	|  102 ms 	|   77.9/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  2 	|  8 	|   5 min	|  23650 	|  23650/0 	|  101 ms 	|   78.8/sec	|
|   JMeter	|  order ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  8 	|   5 min	|  25064 	|  25064/0 	|  94 ms 	|   84/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  1 	|   2 min	|  8779 	|  8779/0 	|  13 ms 	|   73/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  2 	|  1 	|   2 min	|  8278 	|  8278/0 	|  13 ms 	|   68/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  1 	|   2 min	|  6960 	|  6960/0 	|  16 ms 	|   57/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  4 	|   2 min	|  17932 	|  17932/0 	|  26 ms 	|   149/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  2 	|  4 	|   2 min	|  17750 	|  17750/0 	|  26 ms 	|   147/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  4 	|   2 min	|  15929 	|  15929/0 	|  29 ms 	|   132/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  8 	|   2 min	|  21394 	|  21394/0 	|  44 ms 	|   178/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  2 	|  8 	|   2 min	|  21532 	|  21532/0 	|  44 ms 	|   179/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  8 	|   2 min	|  19577 	|  19577/0 	|  48 ms 	|   163/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  1 	|  100 	|   2 min	|  25624 	|  25624/0 	|  469 ms 	|   212/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  2 	|  100 	|   2 min	|  25525 	|  25525/0 	|  470 ms 	|   211/sec	|
|   Java-Quarkus	|  simple ➙ default |  ✅ Mongo DB ➙ clean-before	|  5 	|  100 	|   2 min	|  23237 	|  23237/0 	|  519 ms 	|   191/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  1 	|   2 min	|  15214 	|  15214/0 	|  7 ms 	|   126/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  2 	|  1 	|   2 min	|  10694 	|  10694/0 	|  10 ms 	|   89/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  5 	|  1 	|   2 min	|  9223 	|  9223/0 	|  12 ms 	|   76/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  4 	|   2 min	|  41376 	|  41376/0 	|  11 ms 	|   344/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  2 	|  4 	|   2 min	|  29132 	|  29132/0 	|  15 ms 	|   242/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  5 	|  4 	|   2 min	|  20958 	|  20958/0 	|  22 ms 	|   174/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  8 	|   2 min	|  59936 	|  59936/0 	|  15 ms 	|   499/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  2 	|  8 	|   2 min	|  46515 	|  46515/0 	|  20 ms 	|   387/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  5 	|  8 	|   2 min	|  26142 	|  26142/0 	|  36 ms 	|   217/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  1 	|  100 	|   2 min	|  117212 	|  117212/0 	|  102 ms 	|   971/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  2 	|  100 	|   2 min	|  118294 	|  118294/0 	|  100 ms 	|   985/sec	|
|   Java-Quarkus	|  simple ➙ pool&workers=100 |  ✅ Mongo DB ➙ id-index	|  5 	|  100 	|   2 min	|  47730 	|  47730/0 	|  250 ms 	|   397/sec	|
