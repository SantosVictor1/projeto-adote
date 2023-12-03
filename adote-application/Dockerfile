FROM amazoncorretto:17-alpine-jdk

ENV API adote-application.jar

RUN mkdir -p /opt

COPY target/$API /opt/$API

WORKDIR /opt

ENTRYPOINT java -Xms512m -Xmx512m -jar $API