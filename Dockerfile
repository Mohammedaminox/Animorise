# 1️⃣ Backend Build (Spring Boot)
FROM maven:3.9.2-eclipse-temurin-17 AS backend-build
WORKDIR /app

# Copy pom.xml first (for caching dependencies)
COPY /pom.xml ./pom.xml

# Copy the source code
COPY ./backend/src ./src

# Run Maven package
RUN mvn clean package -DskipTests

# 2️⃣ Frontend Build (Angular 17)
FROM node:18 AS frontend-build
WORKDIR /app
COPY ./frontend/ ./
RUN npm install && npm run build

# 3️⃣ Runtime Image (Combining Backend & Frontend)
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy backend JAR
COPY --from=backend-build /app/target/*.jar app.jar

# Copy Angular build to be served by Spring Boot
COPY --from=frontend-build /app/dist/frontend /app/static

# Expose backend API port
EXPOSE 8088

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.web.resources.static-locations=file:/app/static/"]
