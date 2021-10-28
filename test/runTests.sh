#!/bin/sh

TEST_CLIENT=$1  # JMETER or QUARKUS
TEST_IDX=$2  # either number or ALL
BATCH_FILE=test-run/batch.json

echo "test client "$TEST_CLIENT
echo "test index "$TEST_IDX

WITH_WARMUP=$(jq -r '.Warmup .enabled' $BATCH_FILE)
NO_OF_TESTS=$(jq '.Tests.runs | length' $BATCH_FILE)

if [[ $TEST_CLIENT = "JMETER" ]]
then
  cd ../test-clients/jmeter-client
elif [[ $TEST_CLIENT = "QUARKUS" ]]
then
  cd ../test-clients/quarkus-client
else
  echo "invalid test client "$TEST_CLIENT
  exit
fi

#run warmup
if [ $WITH_WARMUP = "yes" ]
then
  ./runTest.sh 999 $WITH_WARMUP
fi

if [[ $TEST_IDX = "ALL" ]]
then
  TEST_COUNTER=0
  while [ $TEST_COUNTER -lt $NO_OF_TESTS ]
  do
    #kickoff metrics collection - call Lokeshs REST API here - send interval for polling metrics on application, env (Vm or OCP) to use
    #TODO
    #run test
    ./runTest.sh $TEST_COUNTER "no"
    #request accumulated metrics - call Lokeshs REST API here
    #TODO
    TEST_COUNTER=$((TEST_COUNTER+1))
  done
else
    #kickoff metrics collection - call Lokeshs REST API here - send interval for polling metrics on application, env (Vm or OCP) to use
    #TODO
    #run test
    ./runTest.sh $TEST_IDX "no"
    #request accumulated metrics - call Lokeshs REST API here
    #TODO
fi
