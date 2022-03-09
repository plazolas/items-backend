# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

ENV MYSQL_HOST=172.17.0.3
ENV APP_DB_USER=root
ENV APP_DB_PASSWORD=deveops

# Copy war file
COPY ./target/items-backend-0.0.1-SNAPSHOT.jar /items-backend-0.0.1-SNAPSHOT.jar

# run the app
CMD ["/usr/bin/java", "-jar", "/items-backend-0.0.1-SNAPSHOT.jar"]
