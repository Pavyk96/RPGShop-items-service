FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/rpg-shop-0.0.1-SNAPSHOT.jar /app/rpg-shop.jar

ENTRYPOINT ["java", "-jar", "/app/rpg-shop.jar"]
