FROM openjdk:17
EXPOSE 1109
ADD ./target/gateway-service-0.0.1-SNAPSHOT.jar gateway.jar
ENTRYPOINT [ "java", "-jar", "/gateway.jar" ]