#define base docker image

FROM openjdk:17

LABEL maintainer="khalid.walit"

ADD target/spring-boot-0.0.1-SNAPSHOT.jar springboot-docker.jar

ENTRYPOINT ["java", "-jar", "springboot-docker.jar"]