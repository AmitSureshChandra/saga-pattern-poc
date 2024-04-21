FROM maven:3-eclipse-temurin-17-alpine
WORKDIR /opt/app
COPY event-log-service/pom.xml ./
COPY event-log-service/src ./src
COPY pom.xml ../
EXPOSE 8080

RUN mvn clean package -DskipTests=true

ENTRYPOINT ["java", "-jar", "/opt/app/target/event-log-service-0.0.1-SNAPSHOT.jar"]

