#!/bin/sh

TEST_IDX=$1
IS_WARMUP=$2
TEST_PATH=../../test/test-run
TEST_FILE=$TEST_PATH/batch.json
TEST_RESULTS=$TEST_PATH/results
JMETER_HOME=$( cat jmeterHome.txt )

echo "***JMETER_HOME: "$JMETER_HOME "***TEST_FILE: "$TEST_FILE "***TEST_IDX: "$TEST_IDX "***IS_WARMUP: "$IS_WARMUP

EP_SCHEMA=$(jq -r '.Process .schema' $TEST_FILE)
EP_URL=$(jq -r '.Process .url' $TEST_FILE)
EP_PORT=$(jq '.Process .port' $TEST_FILE)
TEST_APP=$(jq -r '.Process .app' $TEST_FILE)

echo "***EP_SCHEMA: "$EP_SCHEMA "***EP_URL: "$EP_URL "***EP_PORT: "$EP_PORT "***TEST_APP: "$TEST_APP

if [ $IS_WARMUP = "yes"  ]
then
  REPLICAS=$(jq '.Warmup .replicas' $TEST_FILE)
  USERS=$(jq '.Warmup .users' $TEST_FILE)
  TYPE=$(jq -r '.Warmup .type' $TEST_FILE)
  COUNT=$(jq '.Warmup .timeOrCount' $TEST_FILE)
else
  REPLICAS=$(jq '.Tests.runs['$TEST_IDX'] .replicas' $TEST_FILE)
  USERS=$(jq '.Tests.runs['$TEST_IDX'] .users' $TEST_FILE)
  TYPE=$(jq -r '.Tests .type' $TEST_FILE)
  COUNT=$(jq '.Tests .timeOrCount' $TEST_FILE)
fi

echo "***REPLICAS: "$REPLICAS "***USERS: "$USERS "***TYPE: "$TYPE "***COUNT: "$COUNT

DURATION=0
REQUESTS=0
if [ "$TYPE" = "requests" ]
then
  REQUESTS=$COUNT
elif [ "$TYPE" = "duration" ]
then
  DURATION=$COUNT
else
  echo "not a valid test type found - expected requests or duration"
  exit
fi

if [ $TEST_APP != "order" ] && [ $TEST_APP != "simpleHT" ] && [ $TEST_APP != "fruits" ]
then
  echo "not a valid TEST_APP found - expected order, simpleHT or fruits"
  exit
fi
echo "***TEST_APP: "$TEST_APP

#all test files need to be named according to the following naming convention:
# they start with hardcoded "Users"
# + the test type which is either "duration" or "requests", first capital letter in file name
# + the test application which is "order" or "simpleHT" or fruits, first letter capital in file name
TEST_CASE="Users"${TYPE^}${TEST_APP^}".jmx"
echo "***TEST_CASE: "$TEST_CASE

echo "**********************************************"
echo "************* starting run *******************"

WARMUP_POSTFIX=""
if [ $IS_WARMUP = "yes" ]
then
  WARMUP_POSTFIX="warmup"
fi

TEST_RUN="$JMETER_HOME/bin/jmeter -n -t $TEST_CASE \
-Jschema=$EP_SCHEMA \
-Jurl=$EP_URL \
-Jport=$EP_PORT \
-Jusers=$USERS \
-Jduration=$DURATION \
-Jrequests=$REQUESTS \
-l $TEST_RESULTS/res$TEST_IDX$WARMUP_POSTFIX.jtl"
echo "***TEST_RUN: "$TEST_RUN
$TEST_RUN

echo "**********************************************"
echo "************* finished run *******************"

if [ $IS_WARMUP = "no" ]
then
  TEST_AGG="$JMETER_HOME/bin/JMeterPluginsCMD.sh \
  --generate-csv $TEST_RESULTS/res$TEST_IDX.csv \
  --input-jtl $TEST_RESULTS/res$TEST_IDX.jtl \
  --plugin-type AggregateReport"
  echo "***TEST_AGG: "$TEST_AGG
  $TEST_AGG

  echo "**********************************************"
  echo "******* after aggregate file creation ********"
fi