#!/usr/bin/env bash

mvn clean package

echo 'starting application...'

java -jar taget/wicket-application-0.0.1-SNAPSHOT.jar > log.txt &