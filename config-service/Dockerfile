# Etapa 1: Build
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /build
COPY ../../ .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final
FROM openjdk:17-jdk-alpine
WORKDIR /app
RUN apk add --no-cache curl
COPY --from=build /build/config-service/target/config-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]