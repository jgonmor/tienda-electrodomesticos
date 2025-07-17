FROM openjdk:17-jdk-slim
LABEL authors="jgonmor"

ARG JAR_FILE=target/cart-service-0.0.1.jar
COPY ${JAR_FILE} cart-service.jar
ENTRYPOINT ["java","-jar","cart-service.jar"]