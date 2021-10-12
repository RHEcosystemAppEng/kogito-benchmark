FROM registry.access.redhat.com/ubi8/openjdk-11

WORKDIR /usr/app

COPY ./target/persistGatlingReportsToMongodb-1.0.0-jar-with-dependencies.jar .

CMD ["java","-jar","persistGatlingReportsToMongodb-1.0.0-jar-with-dependencies.jar"]