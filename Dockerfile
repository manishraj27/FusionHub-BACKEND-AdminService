# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/FusionHub-AdminService-0.0.1-SNAPSHOT.jar FusionHub-AdminService.jar

# Expose the application port
EXPOSE 8082

# Command to run the application
ENTRYPOINT ["java", "-jar", "FusionHub-AdminService.jar"]