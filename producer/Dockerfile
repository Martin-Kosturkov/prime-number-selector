FROM openjdk:21-jdk
COPY target/pro*.jar producer.jar
ENTRYPOINT ["java", "-jar", "/producer.jar"]