FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gradle/ gradle/
COPY gradlew build.gradle.kts settings.gradle.kts ./

COPY src/ src/

RUN chmod +x gradlew

RUN ./gradlew bootJar --no-daemon

EXPOSE 8080

CMD ["java", "-jar", "build/libs/shalion-0.0.1-SNAPSHOT.jar"]
