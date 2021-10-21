#!/bin/sh

TEST_IDX=$1
TEST_FILE=test-run/batch.json
JMETER_HOME=$( cat jmeterHome.txt )

echo "***JMETER_HOME: "$JMETER_HOME "***TEST_FILE: "$TEST_FILE "***TEST_IDX: "$TEST_IDX

EP_SCHEMA=$(jq -r '.Process .schema' $TEST_FILE)
EP_URL=$(jq -r '.Process .url' $TEST_FILE)
EP_PORT=$(jq '.Process .port' $TEST_FILE)
TEST_APP=$(jq -r '.Process .app' $TEST_FILE)
WITH_WARMUP=$(jq -r '.Warmup .enabled' $TEST_FILE)

echo "***EP_SCHEMA: "$EP_SCHEMA "***EP_URL: "$EP_URL "***EP_PORT: "$EP_PORT "***TEST_APP: "$TEST_APP "***WITH_WARMUP: "$WITH_WARMUP

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

if [ $WITH_WARMUP = "yes" ]
then
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
	-l test-run/results/res"$TEST_IDX"warmup.jtl"
	echo "***TEST_RUN: "$TEST_RUN
	$TEST_RUN

	echo "**********************************************"
	echo "************* finished warmup run *******************"
fi

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
-l test-run/results/res$TEST_IDX.jtl"
echo "***TEST_RUN: "$TEST_RUN
$TEST_RUN

echo "**********************************************"
echo "************* finished run *******************"

TEST_AGG="$JMETER_HOME/bin/JMeterPluginsCMD.sh \
--generate-csv test-run/results/res$TEST_IDX.csv \
--input-jtl test-run/results/res$TEST_IDX.jtl \
--plugin-type AggregateReport"
echo "***TEST_AGG: "$TEST_AGG
$TEST_AGG

echo "**********************************************"
echo "******* after aggregate file creation ********"