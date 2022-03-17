# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

ENV MYSQL_HOST=172.17.0.6
ENV spring_profiles_active=prod

# Copy war file
COPY ./target/items-backend-0.0.1-SNAPSHOT.jar /items-backend-0.0.1-SNAPSHOT.jar

# run the app
CMD ["/usr/bin/java", "-jar", "/items-backend-0.0.1-SNAPSHOT.jar"]
