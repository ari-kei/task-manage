FROM sapmachine:21.0.3-jdk-ubuntu
WORKDIR /app
COPY ./app/target/app-0.0.1-SNAPSHOT.jar ./

ARG ENV
EXPOSE 8081

CMD [ "java", "-jar", "app-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=${ENV}" ]