FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/bfhl-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dserver.port=${PORT:-8080}","-jar","/app.jar"]