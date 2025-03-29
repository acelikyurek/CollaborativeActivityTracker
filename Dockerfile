FROM maven:3.9.7-eclipse-temurin-22
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
CMD ["java", "-jar", "target/CollaborativeActivityTracker-0.0.1-SNAPSHOT.jar"]
