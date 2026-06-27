FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Primero descarga las dependencias (esto se cachea)
RUN mvn dependency:go-offline -B
COPY src ./src
# Compila sin tests para la imagen Docker
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiar desde la etapa de build
COPY --from=build /app/target/*.jar app.jar
EXPOSE 1428
ENV SERVER_PORT=1428
ENTRYPOINT ["java", "-jar", "app.jar"]