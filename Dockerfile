# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim as builder

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Create a non-root user for security
RUN useradd -m appuser
USER appuser

# Copy the generated JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Set environment variables for the JVM
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]