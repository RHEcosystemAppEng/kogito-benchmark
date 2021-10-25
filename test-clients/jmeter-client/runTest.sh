#!/bin/sh

TEST_IDX=$1
TEST_PATH=../../test/test-run
TEST_FILE=$TEST_PATH/batch.json
TEST_RESULTS=$TEST_PATH/results
JMETER_HOME=$( cat jmeterHome.txt )

echo "***JMETER_HOME: "$JMETER_HOME "***TEST_FILE: "$TEST_FILE "***TEST_IDX: "$TEST_IDX

EP_SCHEMA=$(jq -r '.Process .schema' $TEST_FILE)
EP_URL=$(jq -r '.Process .url' $TEST_FILE)
EP_PORT=$(jq '.Process .port' $TEST_FILE)
TEST_APP=$(jq -r '.Process .app' $TEST_FILE)

echo "***EP_SCHEMA: "$EP_SCHEMA "***EP_URL: "$EP_URL "***EP_PORT: "$EP_PORT "***TEST_APP: "$TEST_APP

REPLICAS=$(jq '.Tests['$TEST_IDX'] .replicas' $TEST_FILE)
USERS=$(jq '.Tests['$TEST_IDX'] .users' $TEST_FILE)
REQUESTS=$(jq '.Tests['$TEST_IDX'] .requests' $TEST_FILE)
DURATION=$(jq '.Tests['$TEST_IDX'] .duration' $TEST_FILE)

echo "***REPLICAS: "$REPLICAS "***USERS: "$USERS "***REQUESTS: "$REQUESTS "***DURATION: "$DURATION

TEST_TYPE="NOT SET"
if [ $REQUESTS != null ]
then
  TEST_TYPE="requests"
elif [ $DURATION != null ]
then
  TEST_TYPE="duration"
else
  echo "not a valid test_type found - expected requests or duration data"
  exit
fi
echo "***TEST_TYPE: "$TEST_TYPE

if [ $TEST_APP != "order" ] && [ $TEST_APP != "simpleHT" ]
then
  echo "not a valid TEST_APP found - expected order, simpleHT"
  exit
fi
echo "***TEST_APP: "$TEST_APP

echo "**********************************************"
echo "************* starting run *******************"

#all test files need to be named according to the following naming convention:
# they start with hardcoded "Users"
# + the test type which is either "duration" or "requests", first capital letter in file name
# + the test application which is "order" or "simpleHT", first letter capital in file name
TEST_CASE="Users"${TEST_TYPE^}${TEST_APP^}".jmx"
echo "***TEST_CASE: "$TEST_CASE

TEST_RUN="$JMETER_HOME/bin/jmeter -n -t $TEST_CASE \
-Jschema=$EP_SCHEMA \
-Jurl=$EP_URL \
-Jport=$EP_PORT \
-Jusers=$USERS \
-Jduration=$DURATION \
-Jrequests=$REQUESTS \
-l $TEST_RESULTS/res$TEST_IDX.jtl"
echo "***TEST_RUN: "$TEST_RUN
$TEST_RUN

echo "**********************************************"
echo "************* finished run *******************"

TEST_AGG="$JMETER_HOME/bin/JMeterPluginsCMD.sh \
--generate-csv $TEST_RESULTS/res$TEST_IDX.csv \
--input-jtl $TEST_RESULTS/res$TEST_IDX.jtl \
--plugin-type AggregateReport"
echo "***TEST_AGG: "$TEST_AGG
$TEST_AGG

echo "**********************************************"
echo "******* after aggregate file creation ********"