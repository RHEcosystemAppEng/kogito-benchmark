#!/bin/sh

TEST_IDX=$1
TEST_PATH=../../test/test-run/
TEST_FILE=$TEST_PATH/batch.json
TEST_RESULTS=$TEST_PATH/results
JMETER_HOME=$( cat jmeterHome.txt )

echo "***JMETER_HOME: "$JMETER_HOME "***TEST_FILE: "$TEST_FILE "***TEST_IDX: "$TEST_IDX

EP_SCHEMA=$(jq -r '.Process .schema' $TEST_FILE)
EP_URL=$(jq -r '.Process .url' $TEST_FILE)
EP_PORT=$(jq '.Process .port' $TEST_FILE)
TEST_APP=$(jq -r '.Process .app' $TEST_FILE)
WITH_WARMUP=$(jq -r '.Warmup .enabled' $TEST_FILE)

echo "***EP_SCHEMA: "$EP_SCHEMA "***EP_URL: "$EP_URL "***EP_PORT: "$EP_PORT "***TEST_APP: "$TEST_APP "***WITH_WARMUP: "$WITH_WARMUP

if [ $WITH_WARMUP != "yes" ]
then
  echo "not running warmup"
  exit
fi

if [ $TEST_APP != "order" ] && [ $TEST_APP != "simpleHT" ]
then
  echo "not a valid TEST_APP found - expected order, simpleHT"
  exit
fi
echo "***TEST_APP: "$TEST_APP

echo "**********************************************"
echo "************* starting warmup run *******************"

#warmup tests are always run on the same application like the real test, but different data
TEST_CASE_WARMUP="UsersDuration"${TEST_APP^}".jmx"
WARMUP_USERS=$(jq '.Warmup .users' $TEST_FILE)
WARMUP_DURATION=$(jq '.Warmup .duration' $TEST_FILE)
echo "***TEST_CASE_WARMUP: "$TEST_CASE_WARMUP "***WARMUP_USERS: "$WARMUP_USERS "***WARMUP_DURATION: "$WARMUP_DURATION

TEST_RUN="$JMETER_HOME/bin/jmeter -n -t $TEST_CASE_WARMUP \
-Jschema=$EP_SCHEMA \
-Jurl=$EP_URL \
-Jport=$EP_PORT \
-Jusers=$WARMUP_USERS \
-Jduration=$WARMUP_DURATION \
-Jrequests=0 \
-l $TEST_RESULTS/res"$TEST_IDX"warmup.jtl"
echo "***TEST_RUN: "$TEST_RUN
$TEST_RUN

echo "**********************************************"
echo "************* finished warmup run *******************"
