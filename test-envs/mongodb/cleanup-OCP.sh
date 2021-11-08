#!/bin/sh
oc cp `dirname "$0"`/clean-mongo.js  -c mongod kogito-mongodb-1:/tmp
oc exec  -c mongod kogito-mongodb-1 -- bash -c "mongo -u developer -p password < /tmp/clean-mongo.js" > /dev/null

