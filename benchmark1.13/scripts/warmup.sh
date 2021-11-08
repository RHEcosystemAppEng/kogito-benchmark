#!/bin/bash

DURATION=$1
THREADS=$2
echo "Warmup execution for $DURATION seconds and $THREADS threads"

CLIENT_POD=`oc get pods -o jsonpath='{..metadata.name}{"\n"}' --selector=app.kubernetes.io/name=kogito-benchmark-client`
REPLICAS=`oc get deployment process-quarkus-example -o jsonpath='{..spec.replicas}{"\n"}'`
echo "Warmup execution from $CLIENT_POD against $REPLICAS replicas"
oc exec "$CLIENT_POD" -- curl -fs -X GET http://localhost:9090/benchmark/simple/$DURATION/$THREADS > /dev/null
