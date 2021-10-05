## Running JMeter from command line

.jmx = the JMeter test definition to run - either run test for users for a given duration (UsersDuration.jmx) or run as requests per user (UsersRequests.jmx)

users: number of users/threads

duration: time to run test

requests: number of requests to run for each user/thread

results: output file incl path

```shell script
./apache-jmeter-5.4.1/bin/jmeter -n -t /home/uegozi/code/kiegroup/kogito-benchmark/test/kogito-jmeter-load-testing/UsersDuration.jmx -Jusers=2 -Jduration=15 -Jresults="/home/uegozi/code/kiegroup/kogito-benchmark/test/kogito-jmeter-load-testing/UsersDuration.csv"

./apache-jmeter-5.4.1/bin/jmeter -n -t /home/uegozi/code/kiegroup/kogito-benchmark/test/kogito-jmeter-load-testing/UsersRequests.jmx -Jusers=2 -Jrequests=15 -Jresults="/home/uegozi/code/kiegroup/kogito-benchmark/test/kogito-jmeter-load-testing/UsersRequests.csv"
...
