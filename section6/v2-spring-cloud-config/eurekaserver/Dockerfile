FROM openjdk:21
COPY build/libs/eurekaserver-0.0.1-SNAPSHOT.jar eurekaserver.jar
COPY build/dependency/opentelemetry-javaagent.jar /app/libs/opentelemetry-javaagent.jar
ENTRYPOINT ["java", "-javaagent:/app/libs/opentelemetry-javaagent.jar", "-jar", "eurekaserver.jar"]
EXPOSE 8070