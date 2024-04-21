FROM maven:3-eclipse-temurin-17-alpine
WORKDIR /opt/app
COPY gateway-service/pom.xml ./
COPY gateway-service/src ./src
COPY pom.xml ../
EXPOSE 8080

RUN mvn clean package -DskipTests=true

ENTRYPOINT ["java", "-jar", "/opt/app/target/gateway-service-0.0.1-SNAPSHOT.jar"]

