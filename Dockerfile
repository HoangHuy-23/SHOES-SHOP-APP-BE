# syntax=docker/dockerfile:1
#FROM maven:3.8.5-openjdk-17
FROM openjdk:oraclelinux8
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

#CMD mvn spring-boot:run

CMD ["./mvnw", "spring-boot:run"]
## Base image được sử dụng để build image
#FROM --platform=amd64 openjdk:17.0.2-oraclelinux8
#
#LABEL authors="doantri"
#
## Set working directory trong container
#WORKDIR /truyentranh_backend
#
## Copy file JAR được build từ ứng dụng Spring Boot vào working directory trong container
#COPY target/TruyenTranh-API-0.0.1-SNAPSHOT.jar TruyenTranh-API-0.0.1-SNAPSHOT.jar
#
## Expose port của ứng dụng
#EXPOSE 8080
#
## Chỉ định command để chạy ứng dụng khi container khởi chạy
#CMD ["java", "-jar", "TruyenTranh-API-0.0.1-SNAPSHOT.jar"]