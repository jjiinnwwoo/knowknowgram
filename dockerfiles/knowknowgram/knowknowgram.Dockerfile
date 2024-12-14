FROM openjdk:17-alpine

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /knowknowgram.jar

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod", "/knowknowgram.jar"]