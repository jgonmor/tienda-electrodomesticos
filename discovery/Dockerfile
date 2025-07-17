FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/discovery-0.0.1.jar
COPY ${JAR_FILE} discovery.jar
ENTRYPOINT ["java","-jar","discovery.jar"]