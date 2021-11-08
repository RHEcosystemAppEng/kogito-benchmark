#!/bin/bash

DEPLOYMENT_ENV=`oc get deployment process-quarkus-example -o jsonpath='{..spec.containers[0].env}{"\n"}'`
MIN_POOL=`echo "$DEPLOYMENT_ENV" | jq -r '.[] | select(.name=="MIN_POOL").value'`
MAX_POOL=`echo "$DEPLOYMENT_ENV" | jq -r '.[] | select(.name=="MAX_POOL").value'`
WORKER_POOL_SIZE=`echo "$DEPLOYMENT_ENV" | jq -r '.[] | select(.name=="WORKER_POOL_SIZE").value'`

VERSION=`git rev-parse --abbrev-ref HEAD`
OCP=`oc whoami --show-console=true`

KOGITO_LIMITS=`oc get deployment process-quarkus-example -o jsonpath='{..spec.containers[0].resources}{"\n"}' | sed 's/"//g'`
MONGODB_LIMITS=`oc get statefulset kogito-mongodb -o jsonpath='{..spec.containers[0].resources}{"\n"}' | sed 's/"//g'`

rm -rf test-run/
mkdir -p test-run

cp ./batch-template.json test-run/batch.json
sed -i -e "s/_VERSION_/${VERSION}/g" test-run/batch.json
sed -i -e "s~_OCP_~${OCP}~g" test-run/batch.json
sed -i -e "s/_MIN_POOL_/${MIN_POOL}/g" test-run/batch.json
sed -i -e "s/_MAX_POOL_/${MAX_POOL}/g" test-run/batch.json
sed -i -e "s/_WORKER_POOL_SIZE_/${WORKER_POOL_SIZE}/g" test-run/batch.json
sed -i -e "s/_KOGITO_LIMITS_/${KOGITO_LIMITS}/g" test-run/batch.json
sed -i -e "s/_MONGODB_LIMITS_/${MONGODB_LIMITS}/g" test-run/batch.json

WARMUP_DURATION=`cat test-run/batch.json | jq -r '.Warmup.timeOrCount'`
WARMUP_USERS=`cat test-run/batch.json | jq -r '.Warmup.users'`
DURATION=`cat test-run/batch.json | jq -r '.Tests.timeOrCount'`
REPLICAS=`cat test-run/batch.json | jq -r '[.Tests.runs[].replicas] | unique | @sh'`
USERS=`cat test-run/batch.json | jq -r '[.Tests.runs[].users] | unique | @sh'`
REPEATS=`cat test-run/batch.json | jq -r '[.Tests.repeats] | unique | @sh'`
if [ "$REPEATS" == "" ]
then
  REPEATS=1
fi

echo "*********************************"
echo "*********************************"
echo " Starting test session for:"
echo " Replicas: [$REPLICAS] "
echo " Users: [$USERS] "
echo " Repeats: [$REPEATS] "
echo "*********************************"
echo "*********************************"

echo "Scaling down to 0"
oc scale deployment process-quarkus-example --replicas 0

session=1
while [ $session -le $REPEATS ]
do
  for r in ${REPLICAS}
  do
    CURR_REPLICAS=`oc get deployment process-quarkus-example -o jsonpath='{..spec.replicas}{"\n"}'`
    if [ $r != $CURR_REPLICAS ]
    then
      echo "Scaling deployment to 0->$r replicas"
      oc scale deployment process-quarkus-example --replicas 0
      oc scale deployment process-quarkus-example --replicas $r
    fi
    ./scripts/warmup.sh $WARMUP_DURATION $WARMUP_USERS

    CLIENT_POD=`oc get pods -o jsonpath='{..metadata.name}{"\n"}' --selector=app.kubernetes.io/name=kogito-benchmark-client`
    if [ "$CLIENT_POD" ]
    then
      echo "Restarting client pod"
      oc delete pod $CLIENT_POD
      sleep 2
    fi

    for t in ${USERS}
    do
      echo "Testing with $t threads"
      echo "Cleaning DB collections"
      ../test-envs/mongodb/cleanup-OCP.sh

      echo "*********************************"
      echo " Replicas: $r"
      echo " Threads: $t"
      echo " Round: $session"
      echo "*********************************"
      ./scripts/run_and_collect_metrics.sh $DURATION $t "test-run/test-$session-$r-$t"
    done
  done
  let "session++"
done

ZIP_NAME=test-${REPEATS}x${WORKER_POOL_SIZE}-${MIN_POOL}-${MAX_POOL}.zip
zip -r ${ZIP_NAME} test-run
echo "Results archived in $ZIP_NAME"
