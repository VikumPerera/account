#Start with a base image containing JAVA runtime
FROM openjdk:21-jdk-slim

#Add Maintainer Info
LABEL maintainer="vikumchathuranga92@gmail.com"

#Add the application jar to the image
COPY target/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar

#when someone is trying to create a container, run this command
ENTRYPOINT ["java", "-jar", "/account-0.0.1-SNAPSHOT.jar"]