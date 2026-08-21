FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src src
RUN mvn -q package
FROM eclipse-temurin:21-jre-alpine
RUN addgroup -S rollnexa && adduser -S rollnexa -G rollnexa
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
USER rollnexa
EXPOSE 8080
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-jar","app.jar"]

