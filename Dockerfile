FROM eclipse-temurin:21-jre-alpine AS layers

ARG YANDEX_OAUTH_TOKEN
ARG FOLDER_ID

ENV YANDEX_OAUTH_TOKEN=${YANDEX_OAUTH_TOKEN}
ENV FOLDER_ID=${FOLDER_ID}

WORKDIR /application
COPY build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
RUN adduser -S spring-user
USER spring-user
COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
