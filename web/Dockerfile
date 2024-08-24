FROM node:22.6.0-bullseye-slim

ENV APP /app
USER 1000:1000
WORKDIR $APP
EXPOSE 5173

COPY ./web/package.json $APP
RUN npm install

COPY ./web $APP