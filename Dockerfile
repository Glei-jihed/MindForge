FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail dans le container
WORKDIR /app

# Copier le JAR de l'application dans le container
COPY target/mindforge-0.0.1-SNAPSHOT.jar /app/mindforge.jar

# Exposer le port 8080 (celui utilisé par l’application Spring Boot)
EXPOSE 8080

# Commande de démarrage de l’application
ENTRYPOINT ["java", "-jar", "/app/mindforge.jar"]
