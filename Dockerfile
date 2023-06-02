# FROM openjdk:17-jdk-alpine
# COPY target/car_reservation_api-0.0.1-SNAPSHOT.jar car_reservation.jar
# ENTRYPOINT [ "java", "-jar", "car_reservation.jar"]

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*8000'"]

FROM base as build
RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 5030
COPY --from=build /app/target/car_reservation_api-0.0.1-SNAPSHOT.jar /car_reservation.jar
CMD [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "car_reservation.jar" ]