FROM eclipse-temurin:21-jre

LABEL com.user.api_backend="user-api" \
        author="Anne-Laure"

WORKDIR /app

COPY target/*.jar user.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "user.jar"]