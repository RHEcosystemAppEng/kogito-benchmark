#!/bin/bash

if [ ! -f $PWD/test.properties ]; then
	echo "Missing configuration file test.properties. Creating an empty one for you"
	echo "export baseURL=http://localhost:8080
export serviceName=emptyProcess
export numberOfUsers=1 
export testCase=CONST_USERS_PER_SEC
export maxDuration=10
export duration=1" > $PWD/test.properties;
	exit 1;
fi

. $PWD/test.properties

echo "Test properties are:
	baseURL: $baseURL
	serviceName: $serviceName
	numberOfUsers: $numberOfUsers
	testCase: $testCase
	maxDuration: $maxDuration
	duration: $duration"

mvn gatling:test -Dgatling.simulationClass=com.redhat.kogito.gatling.simulation.KogitoOrderProcessLoadTestSimulation
