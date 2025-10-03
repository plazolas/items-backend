# Alpine Linux with OpenJDK JRE
FROM openjdk:17-jdk-alpine

ENV MYSQL_HOST=host.docker.internal
ENV spring_profiles_active=dev

# Copy war file
COPY ./target/items-backend-0.0.1.jar /items-backend-0.0.1.jar

EXPOSE 8077
# run the app on dev profile
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "/items-backend-0.0.1.jar"]
