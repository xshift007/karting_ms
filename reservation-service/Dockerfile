# syntax=docker/dockerfile:1.5
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B dependency:go-offline

COPY src src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60"
EXPOSE 8080 9090
ENTRYPOINT ["java","-jar","/app/app.jar"]
