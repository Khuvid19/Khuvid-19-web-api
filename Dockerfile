FROM openjdk:11-jdk as builder
ARG JAR_FILE=build/libs/*.jar
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]