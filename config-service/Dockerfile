FROM openjdk:17
EXPOSE 7559
ADD ./target/config-service-0.0.1-SNAPSHOT.jar config.jar
ENTRYPOINT [ "java", "-jar", "/config.jar" ]