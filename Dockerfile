# stage 1 : compile source to jar file
FROM maven:3.9.8-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .

# download the dependencies
RUN mvn dependency:go-offline

# copy the source code
COPY src ./src

# build the jar file
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine AS production

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# copy jar from the builder stage
COPY --from=builder /app/target/shopping-0.0.1-SNAPSHOT.jar app.jar

# expose port
EXPOSE 8080

# run the jar
ENTRYPOINT [ "java", "-jar", "app.jar" ]