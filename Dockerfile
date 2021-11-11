FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY run.sh .
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["run.sh"]