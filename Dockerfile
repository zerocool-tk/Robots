FROM debian:buster-slim

RUN apt-get update

RUN apt-get install -y openjdk-11-jre maven

ADD robot-worlds-server/target/robot-worlds-server-0.1.1.jar /srv/robot-worlds-server-0.1.1.jar

ADD /robot-worlds-server/config/WorldSpecs.json /srv/config/WorldSpecs.json

WORKDIR /srv
EXPOSE 5000
CMD ["java", "-jar", "robot-worlds-server-0.1.1.jar"]
