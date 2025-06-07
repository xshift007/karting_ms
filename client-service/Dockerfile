# syntax=docker/dockerfile:1.5
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# capa 1 ─ SÓLO wrapper + pom
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    ./mvnw -B dependency:go-offline

# capa 2 ─ resto del código
COPY src src
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    ./mvnw -B package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60 -XX:+UseSerialGC"
EXPOSE 8080 9090
ENTRYPOINT ["java","-jar","app.jar"]
