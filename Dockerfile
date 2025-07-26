FROM gradle:8.4-jdk17 AS builder
WORKDIR /app

COPY build.gradle settings.gradle gradle.properties gradlew /app/
COPY gradle /app/gradle
RUN ./gradlew --no-daemon build || return 0

COPY . /app
RUN ./gradlew --no-daemon bootJar

FROM eclipse-temurin:17-jdk
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
