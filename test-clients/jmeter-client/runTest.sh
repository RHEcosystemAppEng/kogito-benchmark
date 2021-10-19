#!/bin/sh

TEST_DATA=$1
TEST_DATA='{"replicas":1, "users":1, "requests":2}'
TEST_NO=1
#save as file since needed later for report
echo $TEST_DATA >> test-run/test$TEST_NO.json

TEST_FILE=test-run/batch.json
JMETER_HOME=$( cat jmeterHome.txt )

#echo $JMETER_HOME $TEST_DATA $TEST_FILE

EP_SCHEMA=$(jq '.Process .schema' $TEST_FILE)
EP_URL=$(jq '.Process .url' $TEST_FILE)
EP_PORT=$(jq '.Process .port' $TEST_FILE)
TEST_APP=$(jq -r '.Process .app' $TEST_FILE)

#echo $EP_SCHEMA $EP_URL $EP_PORT $TEST_APP

TEST_TYPE="NOT SET"
REPLICAS=$(echo $TEST_DATA | jq '.replicas')
USERS=$(echo $TEST_DATA | jq '.users')
REQUESTS=0
DURATION=0
hasRequests=$(echo $TEST_DATA | jq 'has("requests")')
hasDuration=$(echo $TEST_DATA | jq 'has("duration")')
if [ $hasRequests = "true" ]
then
  TEST_TYPE="requests"
  REQUESTS=$(echo $TEST_DATA | jq '.requests')
elif [ $hasDuration = "true" ]
then
  TEST_TYPE="duration"
  DURATION=$(echo $TEST_DATA | jq '.duration')
else
  echo "not a valid test_type found - expected requests or duration data"
  exit
fi
#echo $TEST_TYPE

if [ $TEST_APP != "order" ] && [ $TEST_APP != "simpleHT" ]
then
  echo "not a valid TEST_APP found - expected order, simpleHT"
  exit
fi
#echo $TEST_APP

#all test files need to be named according to the following schema: hardcoded "Users"
# + the test type which is either Duration or Requests
# and then the test application which is order or simple
TEST_CASE="Users"${TEST_TYPE^}${TEST_APP^}".jmx"
#echo $TEST_CASE

echo "**********************************************"
echo "************* starting run *******************"

TEST_RUN="$JMETER_HOME/bin/jmeter -n -t $TEST_CASE \
-Jschema=$EP_SCHEMA \
-Jurl=$EP_URL \
-Jport=$EP_PORT \
-Jusers=$USERS \
-Jduration=$DURATION \
-Jrequests=$REQUESTS \
-l test-run/results/res$TEST_NO.jtl"
echo $TEST_RUN
$TEST_RUN

echo "**********************************************"
echo "************* finished run *******************"

TEST_AGG="$JMETER_HOME/bin/JMeterPluginsCMD.sh \
--generate-csv test-run/results/res$TEST_NO.csv \
--input-jtl test-run/results/res$TEST_NO.jtl \
--plugin-type AggregateReport"
echo $TEST_AGG

$TEST_AGG

echo "**********************************************"
echo "******* after aggregate file creation ********"
