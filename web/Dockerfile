# ビルド
FROM node:22.6.0-bullseye-slim as builder

ENV APP /app
WORKDIR $APP

COPY ["./web/package.json", "./web/package-lock.json", "${APP}"]
RUN npm install

COPY ./web $APP
RUN npm run build

# 実行
FROM node:22.6.0-bullseye-slim

ENV APP /app
USER 1000:1000
WORKDIR $APP
EXPOSE 3000

COPY --from=builder /app/public ${APP}/public
COPY --from=builder /app/build ${APP}/build
COPY --from=builder /app/package*.json ${APP}/
CMD [ "npm", "start" ]