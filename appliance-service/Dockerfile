FROM openjdk:17-jdk-slim
LABEL authors="Jgonmor"
ARG JAR_FILE=target/appliance-service-0.0.1.jar
COPY ${JAR_FILE} appliance-service.jar
ENTRYPOINT ["java","-jar","appliance-service.jar"]
