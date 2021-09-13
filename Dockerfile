FROM openjdk:8-jdk-slim
COPY "./build/libs/evaluacion-java-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 9090
ENTRYPOINT ["java","-jar","app.jar"]