#!/bin/bash

while inotifywait -r -e modify /app/src/; 
do 
  ./mvnw compile -o -DskipTests && touch /app/.triggerfile; 
done >/dev/null 2>&1 &

./mvnw spring-boot:run