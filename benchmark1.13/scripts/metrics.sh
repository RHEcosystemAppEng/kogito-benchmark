#!/bin/bash
URL="https://prometheus-k8s-openshift-monitoring.apps.kogito-ocp.hib9.p1.openshiftapps.com/api/v1/query"
TOKEN=`oc whoami -t`
AUTH="Authorization: Bearer $TOKEN"
QUERY=`cat $1`
curl -s --data-urlencode "query=$QUERY" "$URL" --header "$AUTH" | jq -r "$2"