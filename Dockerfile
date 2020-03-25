ARG VERSION=8
#openjdk ist der Image name worauf jdk installiert ist, wir bauen hier ein container namens BUILD
FROM openjdk:${VERSION}-jdk as BUILD
# cp .(local) /src (in container)
COPY . /src
# = cd /src
WORKDIR /src

RUN ./gradlew --no-daemon build
#-----------------------------------------------------------------------------------
FROM openjdk:${VERSION}-jre

COPY --from=BUILD /src/build/libs/springboot-kotlin-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
#container gibt diesen port frei worüber die Anwendung angesprochen werden kann
EXPOSE 8080

CMD ["java","-jar","app.jar"]