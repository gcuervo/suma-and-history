FROM maven:3.8.4 AS build
WORKDIR /challenge
COPY src ./src
COPY pom.xml ./
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/challenge/target/*.jar
COPY --from=build ${JAR_FILE} /challenge.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar","/challenge.jar"]
ENTRYPOINT [ "java", "-jar", "/challenge.jar" ]
