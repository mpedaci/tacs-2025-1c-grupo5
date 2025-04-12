# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21.0.6_7-jre
WORKDIR /app
COPY --from=build /app/target/app.jar ./app.jar

# Usar un usuario no root
RUN groupadd --system appgroup && useradd --system --no-create-home --gid appgroup appuser
USER appuser

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
