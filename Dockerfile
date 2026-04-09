# Stage 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy the pom.xml and download dependencies
# This is done before copying source code to take advantage of Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image with a lightweight JRE
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Add a non-root user for security best practices
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the application
# We use ENTRYPOINT to allow passing environment variables seamlessly when running the container
ENTRYPOINT ["java", "-jar", "app.jar"]
