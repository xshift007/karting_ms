FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
# Copia todo el proyecto para resolver el POM padre
COPY . .
WORKDIR /app/discovery-server
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/discovery-server/target/*.jar app.jar
ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=60"
ENTRYPOINT ["java","-jar","/app/app.jar"]
