FROM openjdk:15-alpine

#heroku has only database_url and token_secret
RUN mkdir -p /workspace
WORKDIR /workspace

COPY /target/bookrest-0.0.1-SNAPSHOT.jar /workspace/bookrest-0.0.1-SNAPSHOT.jar

RUN ls -l

EXPOSE 8080:8080

CMD java -Dserver.port=$PORT -Dspring.profiles.active=prod -jar /workspace/bookrest-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java", "-DSpring.profiles.active=dev", "-jar", "/workspace/bookrest-0.0.1-SNAPSHOT.jar"]
