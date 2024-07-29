# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project files to the working directory
COPY . .

# Build the application
RUN ./mvnw clean install

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/Librarysystem-0.0.1-SNAPSHOT.JAR"]