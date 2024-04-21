FROM maven:3-eclipse-temurin-17-alpine as builder
WORKDIR /opt/app

COPY common-module/pom.xml ./
COPY common-module/src ./src
COPY pom.xml /opt/

RUN mvn clean package -DskipTests=true

FROM maven:3-eclipse-temurin-17-alpine
WORKDIR /opt/app
COPY pom.xml ./

# catalog

COPY catalog-service/src ./catalog-service/src
COPY catalog-service/pom.xml ./catalog-service/
COPY --from=builder /opt/app/target/*.jar  ./catalog-service/lib/

RUN cd catalog-service && mvn clean package -DskipTests=true && cd ..

# event-log
COPY event-log-service/src ./event-log-service/src
COPY event-log-service/pom.xml ./event-log-service/

RUN cd event-log-service && mvn clean package -DskipTests=true && cd ..

# need to add other services

ENTRYPOINT ["java", "-jar", "/opt/app/catalog-service/target/*.jar", ";", "java", "-jar", "/opt/app/event-log-service/target/*.jar"]

