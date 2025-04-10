# Stage 1: Build the application
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /
COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Stage 2: Run the application
FROM openjdk:17-slim
WORKDIR /
COPY --from=build /target/app.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "challenge.jar"]