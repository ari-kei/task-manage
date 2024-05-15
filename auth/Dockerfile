# ビルドステージ
FROM azul/zulu-openjdk:21.0.3-jdk AS builder

# Gradleインストール
RUN apt-get update\
  && apt-get -y install curl\
  zip\
  && curl -s "https://get.sdkman.io" | bash\
  && echo ". $HOME/.sdkman/bin/sdkman-init.sh; sdk install gradle" | bash

WORKDIR /auth
COPY ./auth .
RUN ./gradlew build

# 実行ステージ
FROM azul/zulu-openjdk-alpine:21-jre-headless
WORKDIR /auth
COPY --from=builder /auth/build/libs/auth-0.0.1-SNAPSHOT.jar ./
CMD [ "java", "-jar", "auth-0.0.1-SNAPSHOT.jar" ]