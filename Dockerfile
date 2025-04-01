FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/66bit-0.0.1-SNAPSHOT.jar /app/66bit.jar

ENTRYPOINT ["java", "-jar", "/app/66bit.jar"]
