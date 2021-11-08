#!/bin/bash

DURATION=$1
THREADS=$2
FOLDER=$3
echo "Starting execution for $DURATION seconds and $THREADS threads, test folder is $FOLDER"
CURR_DIR=`dirname "$0"`
FOLDER=$CURR_DIR/../$FOLDER
[ -d "$FOLDER" ] && rm -rf $FOLDER
[ ! -d "$FOLDER" ] && mkdir -p $FOLDER

CLIENT_POD=`oc get pods -o jsonpath='{..metadata.name}{"\n"}' --selector=app.kubernetes.io/name=kogito-benchmark-client`
REPLICAS=`oc get deployment process-quarkus-example -o jsonpath='{..spec.replicas}{"\n"}'`
echo "Collecting execution data from $CLIENT_POD against $REPLICAS replicas"
RESULT=`oc exec "$CLIENT_POD" -- curl -fs -X GET http://localhost:9090/benchmark/simple/$DURATION/$THREADS`
echo "$RESULT" >> $FOLDER/res.json

EXECUTIONS=`echo "$RESULT" | jq -r ".noOfExecutions"`
FAILURES=`echo "$RESULT" | jq -r ".noOfFailures"`
AVERAGE=`echo "$RESULT" | jq -r ".averageResponseTime"`
THROUGHPUT=`echo "$RESULT" | jq -r ".requestsPerSecond"`
MIN_RT=`echo "$RESULT" | jq -r ".minResponseTime.responseTime"`
MAX_RT=`echo "$RESULT" | jq -r ".maxResponseTime.responseTime"`
THROUGHPUT=`echo "$RESULT" | jq -r ".requestsPerSecond"`
PERCENTILE95=`echo "$RESULT" | jq -r ".percentile95"`
PERCENTILE99=`echo "$RESULT" | jq -r ".percentile99"`
echo "Label,# Samples,Average,Median,90% Line,95% Line,99% Line,Min,Max,Error %,Throughput,Received KB/sec,Std. Dev."> $FOLDER/res.csv
echo "NA-label,$EXECUTIONS,$AVERAGE,NA-median,NA-line90, $PERCENTILE95, $PERCENTILE99, $MIN_RT, $MAX_RT, $FAILURES, $THROUGHPUT, NA-receivedkbps, NA-stddev" >> $FOLDER/res.csv

PROMETHUES_DIR=$CURR_DIR/../prometheus
METRICS_SH=$CURR_DIR/metrics.sh

$METRICS_SH $PROMETHUES_DIR/cpu_by_pod.txt | jq -r '["Pod", "CPU Usage"] , (.data.result[] | [.metric.pod , .value[1]]) | @csv' > $FOLDER/cpu_by_pod.csv
CPU_BY_DEPLOYMENT=`$METRICS_SH $PROMETHUES_DIR/cpu_by_deployment.txt | jq -r '.data.result[0].value[1]'`
MONGO_CPU_BY_DEPLOYMENT=`$METRICS_SH $PROMETHUES_DIR/mongo_cpu_by_deployment.txt | jq -r '.data.result[0].value[1]'`
$METRICS_SH $PROMETHUES_DIR/memory_by_pod.txt | jq -r '["Pod", "Memory Usage Bytes"] , (.data.result[] | [.metric.pod , .value[1]]) | @csv' > $FOLDER/memory_by_pod.csv
MEMORY_BY_DEPLOYMENT=`$METRICS_SH $PROMETHUES_DIR/memory_by_deployment.txt | jq -r '.data.result[0].value[1]'`
MONGO_MEMORY_BY_DEPLOYMENT=`$METRICS_SH $PROMETHUES_DIR/mongo_memory_by_deployment.txt | jq -r '.data.result[0].value[1]'`
echo " Kogito CPU Usage, Kogito Memory Usage Bytes, Mongo CPU Usage, Mongo Memory Usage Bytes" > $FOLDER/metrics.csv
echo "$CPU_BY_DEPLOYMENT, $MEMORY_BY_DEPLOYMENT, $MONGO_CPU_BY_DEPLOYMENT, $MONGO_MEMORY_BY_DEPLOYMENT" >> $FOLDER/metrics.csv

