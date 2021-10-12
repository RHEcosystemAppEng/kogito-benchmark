#!/bin/sh
oc cp clean-mongo.js  -c mongod kogito-mongodb-0:/tmp
oc exec  -c mongod kogito-mongodb-0 -- bash -c "mongo -u developer -p mypass < /tmp/clean-mongo.js"

