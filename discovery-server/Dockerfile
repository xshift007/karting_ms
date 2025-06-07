# syntax=docker/dockerfile:1.5
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B dependency:go-offline

COPY src src
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60"
ENTRYPOINT ["java","-jar","/app/app.jar"]
