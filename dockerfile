# Use Maven to build the application
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run the built JAR with Java
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/ExpenseTracker-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
