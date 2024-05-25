# Use the official OpenJDK image as the base image
FROM openjdk:17-slim
WORKDIR /app
COPY target/transferapi-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
