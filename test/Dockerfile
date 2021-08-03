FROM maven:3.8-adoptopenjdk-11 as dev
WORKDIR /src/kogito-gatling
COPY . /src/kogito-gatling
CMD ["mvn", "gatling:test", "-Dgatling.simulationClass=com.redhat.kogito.gatling.simulation.KogitoOrderProcessLoadTestSimulation"]