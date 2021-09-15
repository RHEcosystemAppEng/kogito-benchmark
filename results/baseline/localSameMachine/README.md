CUPS - 5 minutes - warmup of 5 minutes with 10 users done before each actual test run; restart of app after each actual run

| gatlingfilename |	test | users | throughput users/second |
|----|----|----|----|
|kogitoorderprocessloadtestsimulation-20210915074314997|emptyProcess|10|10|


CCU - else same as CUPS

| gatlingfilename |	test | users | throughput users/second |
|----|----|----|----|		
|kogitoorderprocessloadtestsimulation-20210915080240674|emptyProcess|10|9376|
|kogitoorderprocessloadtestsimulation-20210915090923817|notPersistedProcess|10|7473|
|kogitoorderprocessloadtestsimulation-20210915093537040|order|10|60|  

| Run | Latency 95% PCT (ms) | Latency 99% PCT (ms) | Av. Response (ms) | Peak Response (ms) | Error Rate (%)  | Throughput (transactions / s - TPS) | Runtime memory (MiB / pod) | CPU Usage (m / pod) | Runtime startup (ms) |   
|----|----|----|----|----|----|----|----|----|----|
|20210915074314997|2|2|1|10|0|10| | | |
|20210915080240674|1|2|1|48|0|9376| | | |
|20210915090923817|2|3|1|49|0|7473| | | |
|20210915093537040|221|258|164|377|0|60| | | |
