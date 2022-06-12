FROM openjdk:8-jdk-alpine
ADD target/atm-webservices-0.0.1-SNAPSHOT.jar atm-webservices-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh","-c","java jar atm-webservices-0.0.1-SNAPSHOT.jar"]
