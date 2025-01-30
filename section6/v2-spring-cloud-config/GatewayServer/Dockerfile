FROM openjdk:21
COPY build/libs/GatewayServer-0.0.1-SNAPSHOT.jar  gatewayserver.jar
COPY build/dependency/opentelemetry-javaagent.jar /app/libs/opentelemetry-javaagent.jar
ENTRYPOINT ["java", "-javaagent:/app/libs/opentelemetry-javaagent.jar", "-jar", "gatewayserver.jar"]
EXPOSE 8072