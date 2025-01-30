FROM openjdk:21
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts.jar
COPY target/dependency/opentelemetry-javaagent.jar /app/libs/opentelemetry-javaagent.jar
ENTRYPOINT ["java", "-javaagent:/app/libs/opentelemetry-javaagent.jar","-jar", "accounts.jar"]
EXPOSE 8080