FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/config-server-0.0.1.jar
COPY ${JAR_FILE} config-server.jar
ENTRYPOINT ["java","-jar","config-server.jar"]
EXPOSE 8888 8889