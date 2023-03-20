#FROM eclipse-temurin:11
#COPY target/*.jar app.jar

#ENV DB_USERNAME "postgres"
#ENV DB_PASSWORD "mysecretpassword"
#ENV DB_URL "jdbc:postgresql://localhost:5432/software"
#ENV API_KEY ""

#RUN mkdir /logs

#ENTRYPOINT ["java","-jar","/app.jar"]

FROM --platform=linux/x86_64 eclipse-temurin:11.0.18_10-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


FROM eclipse-temurin:11

VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENV DB_USERNAME "postgres"
ENV DB_PASSWORD "mysecretpassword"
ENV DB_URL "jdbc:postgresql://localhost:5432/software"
ENV API_KEY ""

ENTRYPOINT ["java","-cp","app:app/lib/*","arquitectura.software.demo.DemoApplicationKt"]