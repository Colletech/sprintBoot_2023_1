FROM openjdk:17
EXPOSE 5420
ADD ./target/security-0.0.1-SNAPSHOT.jar security.jar
ENTRYPOINT [ "java", "-jar", "/security.jar" ]