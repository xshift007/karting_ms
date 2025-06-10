# syntax=docker/dockerfile:1.5
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# ---------- 1. pom.xml para caché ----------
COPY pom.xml .
COPY config-server/pom.xml         config-server/
COPY discovery-server/pom.xml      discovery-server/
COPY gateway-service/pom.xml       gateway-service/
COPY client-service/pom.xml        client-service/
COPY pricing-service/pom.xml       pricing-service/
COPY reservation-service/pom.xml   reservation-service/
COPY session-service/pom.xml       session-service/
COPY payment-service/pom.xml       payment-service/
COPY report-service/pom.xml        report-service/

RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    mvn -B -q dependency:go-offline

# ---------- 2. código fuente ----------
COPY . .

ARG MODULE_NAME
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    mvn -B -q -pl ${MODULE_NAME} -am clean package -DskipTests -Dmaven.test.skip=true

# ---------- 3. imagen final ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
ARG MODULE_NAME
COPY --from=build /app/${MODULE_NAME}/target/${MODULE_NAME}-*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60 -XX:+UseSerialGC"
EXPOSE 8080 8081 9090
ENTRYPOINT ["java","-jar","app.jar"]
