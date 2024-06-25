# 実行ステージ
FROM azul/zulu-openjdk-alpine:21-jre-headless
WORKDIR /auth
COPY ./auth/target/auth-0.0.1-SNAPSHOT.jar ./

ARG ENV
CMD [ "java", "-jar", "auth-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=${ENV}" ]