# ---------- STAGE 1: Build ----------
FROM gradle:8.5-jdk21-alpine AS builder

WORKDIR /app

# Copy gradle wrapper first (better caching)
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

# Download dependencies (cache layer)
RUN ./gradlew dependencies --no-daemon || true

# Copy source
COPY src src

# Build jar
RUN ./gradlew bootJar --no-daemon

# ---------- STAGE 2: Runtime ----------
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy built jar
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose port (Render/Docker standard)
EXPOSE 8080

# JVM optimizations for containers
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
