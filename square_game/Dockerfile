FROM eclipse-temurin:21-jre

LABEL com.square_game.api_backend="square-game-api" \
        author="Anne-Laure"

WORKDIR /app

COPY target/*.jar square_game.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "square_game.jar"]