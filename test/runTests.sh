#!/bin/sh

TEST_CLIENT=$1  # JMETER or QUARKUS
BATCH_FILE=test-run/batch.json

if [[ $TEST_CLIENT = "'JMETER'" ]]
then
  cd ../test-clients/jmeter-client
elif [[ $TEST_CLIENT = "'QUARKUS'" ]]
then
  cd ../test-clients/quarkus-client
else
  echo "invalid test client "$TEST_CLIENT
  exit
fi

TEST_COUNTER=0
NO_OF_TESTS=$(jq '.Tests | length' $BATCH_FILE)
WITH_WARMUP=$(jq -r '.Warmup .enabled' $BATCH_FILE)
while [ $TEST_COUNTER -lt $NO_OF_TESTS ]
do
  #validate env

  #run warmup
  if [ $WITH_WARMUP = "yes" ]
  then
    ./runTestWarmup.sh $TEST_COUNTER
  fi
  #kickoff metrics collection - call Lokeshs REST API here - send interval for polling metrics on application, env (Vm or OCP) to use
  #TODO
  #run test
  ./runTest.sh $TEST_COUNTER
  #request accumulated metrics - call Lokeshs REST API here
  #TODO
  TEST_COUNTER=$((TEST_COUNTER+1))
done