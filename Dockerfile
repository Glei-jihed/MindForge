FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/mindforge-0.0.1-SNAPSHOT.jar /app/mindforge.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/mindforge.jar"]
