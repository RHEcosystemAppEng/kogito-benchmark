#!/bin/sh

ENV=$1
function prop {
    grep "${1}=" ${ENV}.properties|cut -d'=' -f2
}

JMETER_HOME=$(prop 'jmeter_home')
EP_SCHEMA=$(prop 'schema')
EP_URL=$(prop 'url')
EP_PORT=$(prop 'port')

TEST_TYPE=$(prop 'test_type')
TEST_APP=$(prop 'test_app')
TEST_CASE=
if [ $TEST_TYPE = "duration" ]
then
   if [ $TEST_APP = "order" ]
   then
      TEST_CASE="UsersDurationOrder.jmx"
   elif [ $TEST_APP = "notPersist" ]
   then
      echo "not implemented yet"$TEST_APP
      exit
   else
      echo "not a valid test_app"$TEST_APP
      exit
   fi
elif [ $TEST_TYPE = "requests" ]
then
    if [ $TEST_APP = "order" ]
    then
       TEST_CASE="UsersRequestsOrder.jmx"
    elif [ $TEST_APP = "notPersist" ]
    then
       echo "not implemented yet"$TEST_APP
       exit
    else
       echo "not a valid test_app"$TEST_APP
       exit
    fi
else
   echo "not a valid test_type"$TEST_TYPE
   exit
fi

echo "************* used parameters and properties: "$JMETER_HOME $TEST_CASE $EP_SCHEMA $EP_URL $EP_PORT $(prop 'users')  $(prop 'duration')  $(prop 'requests') $(prop 'results_location')/$(prop 'results_file')

echo "**********************************************"
echo "************* starting run *******************"
TEST_RUN="$JMETER_HOME/bin/jmeter -n -t $TEST_CASE -Jschema=$EP_SCHEMA -Jurl=$EP_URL -Jport=$EP_PORT \
-Jusers=$(prop 'users') -Jduration=$(prop 'duration') -Jrequests=$(prop 'requests') \
-l $(prop 'results_location')/$(prop 'results_file').jtl"
echo $TEST_RUN
$TEST_RUN

echo "**********************************************"
echo "************* finished run *******************"
TEST_AGG="$JMETER_HOME/bin/JMeterPluginsCMD.sh --generate-csv $(prop 'results_location')/$(prop 'results_file').csv \
--input-jtl $(prop 'results_location')/$(prop 'results_file').jtl \
--plugin-type AggregateReport"
echo $TEST_AGG
$TEST_AGG
echo "**********************************************"
echo "******* after aggregate file creation ********"
