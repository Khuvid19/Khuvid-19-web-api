FROM adoptopenjdk/openjdk11
CMD ["./gradlew", "clean", "build"]
ENTRYPOINT ["java", "-jar", "app.jar"]