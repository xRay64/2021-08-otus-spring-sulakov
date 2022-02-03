FROM openjdk:17.0.2-slim
COPY /target/library-app-docker.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
