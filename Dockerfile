FROM openjdk:17-jdk

WORKDIR /app

COPY /parker/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
