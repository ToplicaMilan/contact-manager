FROM openjdk:19
ARG JAR_FILE=*.jar
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]