FROM maven:3-eclipse-temurin-17-alpine as builder
WORKDIR /opt/app

COPY common-module/pom.xml /opt/app/
COPY pom.xml /opt/
COPY common-module/src /opt/app/src

RUN mvn clean package -DskipTests=true

FROM maven:3-eclipse-temurin-17-alpine
WORKDIR /opt/app
COPY catalog-service/pom.xml ./
COPY catalog-service/src ./src
COPY pom.xml ../
COPY --from=builder /opt/app/target/*.jar ./lib/common-module-0.0.1-SNAPSHOT.jar
EXPOSE 8080

RUN mvn clean package -DskipTests=true

ENTRYPOINT ["java", "-jar", "/opt/app/target/catalog-service-0.0.1-SNAPSHOT.jar"]

