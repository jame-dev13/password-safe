# Etapa 1: Build con Maven y Java 21
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar solo el pom.xml para aprovechar el cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente completo
COPY src ./src

# Construir el proyecto (sin ejecutar tests)
RUN mvn clean package -DskipTests

# Etapa 2: Runtime con Java 21 (JDK slim)
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copiar el JAR generado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
