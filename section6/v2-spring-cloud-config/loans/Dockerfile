FROM openjdk:21
COPY target/loans-0.0.1-SNAPSHOT.jar loans.jar
COPY target/dependency/opentelemetry-javaagent.jar /app/libs/opentelemetry-javaagent.jar
ENTRYPOINT ["java", "-javaagent:/app/libs/opentelemetry-javaagent.jar", "-jar", "loans.jar"]
EXPOSE 8072