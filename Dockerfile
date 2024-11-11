FROM openjdk:21-jdk-alpine
VOLUME /tmp
COPY build/libs/forrrest-appmanagementservice.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]