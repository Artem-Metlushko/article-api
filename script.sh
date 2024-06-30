#!/bin/bash
export TERM=dumb
docker container stop  article-api-postgres-1
docker container rm  article-api-postgres-1
docker-compose up -d
./gradlew clean
./gradlew bootJar
java -jar build/libs/article-api-0.0.1-SNAPSHOT.jar