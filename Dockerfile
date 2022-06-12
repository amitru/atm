FROM openjdk:8-jdk-alpine
ADD target/restful-webservices-0.0.1-SNAPSHOT.jar restful-webservices-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh","-c","java jar restful-webservices-0.0.1-SNAPSHOT.jar"]