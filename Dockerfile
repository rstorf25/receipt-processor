FROM gradle:8.12.1-jdk17-jammy AS build
WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

RUN ./gradlew dependencies --daemon

COPY src ./src

RUN ./gradlew clean build -x test --no-daemon --build-cache


FROM eclipse-temurin:17-jdk
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]