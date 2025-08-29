FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file from your local machine to the container
COPY target/bfhl-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (default for Spring Boot is 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]