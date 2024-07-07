# Stage 1: Build Stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
# Menyalin seluruh kode sumber dan file pendukung yang diperlukan
COPY pom.xml /app/
COPY src /app/src
RUN mvn -f /app/pom.xml clean package


# Stage 2: Production Stage
FROM openjdk:17-jdk-alpine
# Menyalin file JAR yang sudah dibuat dari stage build ke stage produksi
COPY --from=build /app/target/*.jar app.jar
# Menjalankan aplikasi menggunakan perintah CMD
CMD ["java", "-jar", "app.jar"]
