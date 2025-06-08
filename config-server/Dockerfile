# syntax=docker/dockerfile:1.5
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copy only the pom.xml to leverage Docker cache
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    mvn -B dependency:go-offline

# 2. Copy the rest of the source code
COPY src src
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    mvn -B package -DskipTests

# Final stage: create a minimal JRE-based image
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60 -XX:+UseSerialGC"
ENTRYPOINT ["java","-jar","/app/app.jar"]
