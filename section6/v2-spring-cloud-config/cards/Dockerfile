FROM openjdk:21
COPY target/cards-0.0.1-SNAPSHOT.jar cards.jar
COPY target/dependency/opentelemetry-javaagent.jar /app/libs/opentelemetry-javaagent.jar
ENTRYPOINT ["java", "-javaagent:/app/libs/opentelemetry-javaagent.jar", "-jar", "cards.jar"]
EXPOSE 9000