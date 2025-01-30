FROM openjdk:21
COPY target/configserver-0.0.1-SNAPSHOT.jar configserver.jar
COPY target/dependency/opentelemetry-javaagent.jar /app/libs/opentelemetry-javaagent.jar
ENTRYPOINT ["java", "-javaagent:/app/libs/opentelemetry-javaagent.jar", "-jar", "configserver.jar"]
EXPOSE 8071