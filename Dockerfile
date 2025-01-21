FROM eclipse-temurin:21-jre-alpine

COPY "./target/*.jar" /bank-account-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bank-account-service.jar"]