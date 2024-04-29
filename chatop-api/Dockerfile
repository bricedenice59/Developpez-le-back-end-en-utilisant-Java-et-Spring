FROM openjdk:21
EXPOSE 8081
ARG JAR_FILE=target/chatop-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} springboot-chatop-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev-docker", "-jar", "/springboot-chatop-api.jar"]
