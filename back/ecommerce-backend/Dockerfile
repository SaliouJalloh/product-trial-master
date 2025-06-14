#Generate Build
FROM maven:3-amazoncorretto-21 AS build
WORKDIR /app
COPY ../pom.xml /app
RUN mvn dependency:resolve
COPY .. /app
RUN mvn package -DskipTests

# Dockerize
FROM eclipse-temurin:21.0.1_12-jdk-alpine
COPY --from=build app/target/*.jar ecommerceApplication.jar
EXPOSE 8085
ENTRYPOINT [ "java", "-jar", "ecommerceApplication.jar" ]