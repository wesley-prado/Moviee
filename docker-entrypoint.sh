#!/bin/bash
exec "$@"
#while inotifywait -r -e modify /app/src/; 
#do 
#  ./mvnw compile -o -DskipTests && touch /app/.triggerfile; 
#done >/dev/null 2>&1 &
#
#./mvnw spring-boot:run -Dspring-boot.run.arguments=--logging.level.org.springframework=DEBUG