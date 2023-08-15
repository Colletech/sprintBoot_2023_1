FROM openjdk:17
EXPOSE 7030
ADD ./target/municipality-0.0.1-SNAPSHOT.jar municipality.jar
ENTRYPOINT [ "java", "-jar", "/municipality.jar" ]