FROM openjdk:21-jdk
COPY target/con*.jar consumer.jar
ENTRYPOINT ["java", "-jar", "/consumer.jar"]