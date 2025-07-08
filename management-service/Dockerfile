FROM openjdk:17-jdk-alpine
VOLUME /tmp
WORKDIR /app
EXPOSE 8081
ARG JAR_FILE=target/management-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]